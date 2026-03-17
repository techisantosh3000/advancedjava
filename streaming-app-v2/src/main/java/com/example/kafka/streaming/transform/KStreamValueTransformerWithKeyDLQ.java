package com.example.kafka.streaming.transform;


import com.example.kafka.streaming.config.AccountBalanceProperties;
import com.example.kafka.streaming.constants.CommonConstants;
import com.example.kafka.streaming.model.AccountBalanceEvent;
import com.example.kafka.streaming.model.AccountMetadata;
import com.example.kafka.streaming.model.BalanceEvent;
import com.example.kafka.streaming.model.Tuple4KStreamDLQ;
import com.example.kafka.streaming.util.AccountBalancePublishingUtil;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.streams.kstream.ValueTransformerWithKey;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// ============================================
// 8. Custom Transformers
// ============================================
@Component
public class KStreamValueTransformerWithKeyDLQ implements ValueTransformerWithKey<String, BalanceEvent, Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent>> {

    private static final Logger log = LoggerFactory.getLogger(KStreamValueTransformerWithKeyDLQ.class);
    private ProcessorContext context;
    private KeyValueStore<String, AccountMetadata> accountStateStore;
    private KeyValueStore<String, BalanceEvent> bufferStore;

    @Autowired
    private AccountBalanceProperties properties;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.accountStateStore = context.getStateStore(properties.getAccountStateStoreName());
        this.bufferStore = context.getStateStore(properties.getBufferStoreName());
    }

    @Override
    public Tuple4KStreamDLQ<AccountBalanceEvent, BalanceEvent> transform(String key, BalanceEvent balanceEvent) {
        try {
            // Check if account metadata is available
            AccountMetadata metadata = accountStateStore.get(balanceEvent.getAccountId());

            if (metadata == null) {
                // Buffer the event and wait for metadata
                bufferStore.put(balanceEvent.getAccountId(), balanceEvent);
                log.debug("Buffered balance event for account: {}", balanceEvent.getAccountId());
                return null; // Skip processing for now
            }

            // Transform the event
            AccountBalanceEvent accountBalanceEvent = AccountBalancePublishingUtil
                    .transformToAccountBalanceEvent(balanceEvent, metadata);

            // Remove from buffer if it was there
            bufferStore.delete(balanceEvent.getAccountId());

            return Tuple4KStreamDLQ.ofError(null, accountBalanceEvent, balanceEvent, 0);

        } catch (Exception e) {
            log.error("Error processing balance event for account {}: {}", balanceEvent.getAccountId(), e.getMessage(), e);

            // Get retry count from headers
            Integer retryCount = getRetryCountFromHeaders();

            return Tuple4KStreamDLQ.ofError(e, null, balanceEvent, retryCount);
        }
    }

    private Integer getRetryCountFromHeaders() {
        Headers headers = context.headers();
        Header retryHeader = headers.lastHeader(CommonConstants.RETRY_COUNT_HEADER);
        if (retryHeader != null) {
            return Integer.parseInt(new String(retryHeader.value()));
        }
        return 0;
    }

    @Override
    public void close() {
        // Cleanup if needed
    }
}
