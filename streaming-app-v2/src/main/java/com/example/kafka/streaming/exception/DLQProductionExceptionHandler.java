package com.example.kafka.streaming.exception;

import com.example.kafka.streaming.config.AccountBalanceProperties;
import com.example.kafka.streaming.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// ============================================
// 9. Production Exception Handler
// ============================================
@Component
@Slf4j
public class DLQProductionExceptionHandler implements ProductionExceptionHandler {

    @Autowired
    private AccountBalanceProperties properties;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // Changed to String, String

    @Override
    public ProductionExceptionHandlerResponse handle(ProducerRecord<byte[], byte[]> record, Exception exception) {
        log.error("Production exception occurred: {}", exception.getMessage(), exception);

        try {
            if (isSerializationError(exception)) {
                routeToPoisonTopic(record, exception);
            } else {
                routeToDLQTopic(record, exception);
            }
        } catch (Exception e) {
            log.error("Failed to route error message: {}", e.getMessage(), e);
        }

        return ProductionExceptionHandlerResponse.CONTINUE;
    }

    private boolean isSerializationError(Exception exception) {
        return exception instanceof SerializationException ||
                exception.getCause() instanceof SerializationException;
    }

    private void routeToPoisonTopic(ProducerRecord<byte[], byte[]> record, Exception exception) {
        try {
            // Convert byte arrays to strings
            String key = record.key() != null ? new String(record.key()) : null;
            String value = record.value() != null ? new String(record.value()) : null;

            // Create headers for error information
            Map<String, Object> headers = new HashMap<>();
            headers.put(CommonConstants.ERROR_TYPE_HEADER, "SERIALIZATION_ERROR");
            headers.put(CommonConstants.EXCEPTION_MESSAGE_HEADER, exception.getMessage());
            headers.put(CommonConstants.ORIGINAL_TOPIC_HEADER, record.topic());

            // Send using KafkaTemplate with proper method
            ProducerRecord<String, String> poisonRecord = new ProducerRecord<>(
                    properties.getPoisonTopic(),
                    null, // partition
                    key,
                    value
            );

            // Add headers to the record
            headers.forEach((headerKey, headerValue) ->
                    poisonRecord.headers().add(headerKey, headerValue.toString().getBytes()));

            kafkaTemplate.send(poisonRecord);
            log.warn("Routed serialization error to poison topic: {}", properties.getPoisonTopic());

        } catch (Exception e) {
            log.error("Failed to route to poison topic", e);
        }
    }

    private void routeToDLQTopic(ProducerRecord<byte[], byte[]> record, Exception exception) {
        try {
            // Convert byte arrays to strings
            String key = record.key() != null ? new String(record.key()) : null;
            String value = record.value() != null ? new String(record.value()) : null;

            // Create headers for error information
            Map<String, Object> headers = new HashMap<>();
            headers.put(CommonConstants.ERROR_TYPE_HEADER, "PRODUCTION_ERROR");
            headers.put(CommonConstants.EXCEPTION_MESSAGE_HEADER, exception.getMessage());
            headers.put(CommonConstants.ORIGINAL_TOPIC_HEADER, record.topic());
            headers.put(CommonConstants.RETRY_COUNT_HEADER, "0");

            // Send using KafkaTemplate
            ProducerRecord<String, String> dlqRecord = new ProducerRecord<>(
                    properties.getDlqTopic(),
                    null, // partition
                    key,
                    value
            );

            // Add headers to the record
            headers.forEach((headerKey, headerValue) ->
                    dlqRecord.headers().add(headerKey, headerValue.toString().getBytes()));

            kafkaTemplate.send(dlqRecord);
            log.warn("Routed production error to DLQ topic: {}", properties.getDlqTopic());

        } catch (Exception e) {
            log.error("Failed to route to DLQ topic", e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs) {
        // This method is called when the handler is initialized
        // You can access Kafka Streams configuration here if needed
        log.info("Configuring DLQ Production Exception Handler with configs: {}", configs.keySet());

        // Optional: You can extract specific configurations if needed
        // String bootstrapServers = (String) configs.get(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG);
        // String applicationId = (String) configs.get(StreamsConfig.APPLICATION_ID_CONFIG);
    }
}