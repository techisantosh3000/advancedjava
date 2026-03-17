package com.example.kafka.streaming.processor;

import com.example.kafka.streaming.config.AccountBalanceProperties;
import com.example.kafka.streaming.model.AccountBalanceEvent;
import com.example.kafka.streaming.model.AccountMetadata;
import com.example.kafka.streaming.model.BalanceEvent;
import com.example.kafka.streaming.model.Tuple4KStreamDLQ;
import com.example.kafka.streaming.transform.KStreamValueTransformerWithKeyDLQ;
import com.example.kafka.streaming.util.AccountBalancePublishingUtil;
import com.example.kafka.streaming.util.SerdeUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

// ============================================
// 10. Stream Processing Configuration
// ============================================
@Component
public class AccountBalanceStreamProcessor {

    private static final Logger log = LoggerFactory.getLogger(AccountBalanceStreamProcessor.class);

    @Autowired
    private AccountBalanceProperties properties;

    @Bean
    public Topology buildTopology(StreamsBuilder streamsBuilder) {

        // Create state stores
        createStateStores(streamsBuilder);

        // Account metadata KTable
        KTable<String, AccountMetadata> accountMetadataTable = streamsBuilder
                .table(properties.getAccountMetadataTopic(),
                        Consumed.with(Serdes.String(), createAccountMetadataSerde()),
                        Materialized.as(properties.getAccountStateStoreName()));

        // Main balance events stream
        KStream<String, BalanceEvent> balanceStream = streamsBuilder
                .stream(properties.getInputTopic(),
                        Consumed.with(Serdes.String(), createBalanceEventSerde()));

        // Transform with error handling
        KStream<String, Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent>> transformedStream = balanceStream
                .transformValues(() -> new KStreamValueTransformerWithKeyDLQ(),
                        properties.getAccountStateStoreName(), properties.getBufferStoreName())
                .filter((key, value) -> value != null);

        // Split success and error streams
        Map<String, KStream<String, Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent>>> branches =
                transformedStream.split(Named.as("branch-"))
                        .branch((key, value) -> value.getException() == null, Branched.as("success"))
                        .defaultBranch(Branched.as("error"));

        // Process successful transformations
        processSuccessfulEvents(branches.get("branch-success"));

        // Process error events
        processErrorEvents(branches.get("branch-error"));

        return streamsBuilder.build();
    }

    private void createStateStores(StreamsBuilder streamsBuilder) {
        // Buffer store for temporary storage
        StoreBuilder<KeyValueStore<String, BalanceEvent>> bufferStoreBuilder = Stores
                .keyValueStoreBuilder(Stores.persistentKeyValueStore(properties.getBufferStoreName()),
                        Serdes.String(), createBalanceEventSerde());
        streamsBuilder.addStateStore(bufferStoreBuilder);
    }

    private void processSuccessfulEvents(KStream<String, Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent>> successStream) {
        // Extract successful AccountBalanceEvent
        KStream<String, AccountBalanceEvent> accountBalanceStream = successStream
                .mapValues(tuple -> tuple.getProcessedMessage());

        // Branch by product type
        Map<String, KStream<String, AccountBalanceEvent>> productBranches = accountBalanceStream
                .split(Named.as("product-"))
                .branch((key, value) -> "PSA".equals(value.getProductType()), Branched.as("psa"))
                .branch((key, value) -> "PBL".equals(value.getProductType()), Branched.as("pbl"))
                .defaultBranch(Branched.as("unknown"));

        // Send to respective output topics
        productBranches.get("product-psa")
                .to(properties.getOutputPsaTopic(), Produced.with(Serdes.String(), createAccountBalanceEventSerde()));

        productBranches.get("product-pbl")
                .to(properties.getOutputPblTopic(), Produced.with(Serdes.String(), createAccountBalanceEventSerde()));

        // Log unknown product types
        productBranches.get("product-unknown").foreach((key, value) ->
                log.warn("Unknown product type: {} for account: {}", value.getProductType(), key));
    }

    private void processErrorEvents(KStream<String, Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent>> errorStream) {
        // Split error stream based on retry logic
        Map<String, KStream<String, Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent>>> errorBranches =
                errorStream.split(Named.as("error-"))
                        .branch((key, value) -> !AccountBalancePublishingUtil.isRetriable(value),
                                Branched.as("non-retriable"))
                        .branch((key, value) -> AccountBalancePublishingUtil.isRetriable(value) &&
                                        value.getRetryCount() >= properties.getMaxRetryCount(),
                                Branched.as("exhausted"))
                        .defaultBranch(Branched.as("retriable"));

        // Non-retriable errors → Poison Topic
        errorBranches.get("error-non-retriable")
                .mapValues(tuple -> addErrorHeaders(tuple, "NON_RETRIABLE"))
                .to(properties.getPoisonTopic(), Produced.with(Serdes.String(), createTupleSerde()));

        // Retriable errors (exhausted retries) → DLQ Topic
        errorBranches.get("error-exhausted")
                .mapValues(tuple -> addErrorHeaders(tuple, "RETRY_EXHAUSTED"))
                .to(properties.getDlqTopic(), Produced.with(Serdes.String(), createTupleSerde()));

        // Retriable errors (within limit) → Retry Topic
        errorBranches.get("error-retriable")
                .mapValues(tuple -> incrementRetryCount(tuple))
                .to(properties.getRetryTopic(), Produced.with(Serdes.String(), createBalanceEventSerde()));
    }

    private Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent> addErrorHeaders(
            Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent> tuple, String errorType) {
        // In a real implementation, you'd add headers to the record
        log.info("Adding error headers - Type: {}, Exception: {}", errorType, tuple.getException().getMessage());
        return tuple;
    }

    private BalanceEvent incrementRetryCount(Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent> tuple) {
        // Return original message for retry with incremented count
        // In a real implementation, you'd set the retry count header
        log.info("Incrementing retry count for account: {}, current count: {}",
                tuple.getOriginalMessage().getAccountId(), tuple.getRetryCount());
        return tuple.getOriginalMessage();
    }

    // Serde creation methods
    private Serde<BalanceEvent> createBalanceEventSerde() {
        return SerdeUtils.createJsonSerde(BalanceEvent.class);
    }

    private Serde<AccountBalanceEvent> createAccountBalanceEventSerde() {
        return SerdeUtils.createJsonSerde(AccountBalanceEvent.class);
    }

    private Serde<AccountMetadata> createAccountMetadataSerde() {
        return SerdeUtils.createJsonSerde(AccountMetadata.class);
    }

    private Serde<Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent>> createTupleSerde() {
        return SerdeUtils.createJsonSerde(new TypeReference<Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent>>() {});
    }
}

