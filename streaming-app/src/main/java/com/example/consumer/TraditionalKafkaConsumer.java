package com.example.consumer;


import com.example.model.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TraditionalKafkaConsumer {

    @KafkaListener(topics = "enriched-user-activity")
    public void consumeEnrichedActivity(@Payload String enrichedActivity,
                                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                        @Header(KafkaHeaders.OFFSET) long offset) {
        System.out.printf("Consumed enriched activity from topic: %s, partition: %d, offset: %d%n",
                topic, partition, offset);
        System.out.println("Enriched Activity: " + enrichedActivity);
    }

    @KafkaListener(topics = "high-value-events")
    public void consumeHighValueEvents(@Payload UserEvent event,
                                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.printf("High-value event from topic %s: %s%n", topic, event);
    }

    @KafkaListener(topics = "event-type-counts")
    public void consumeEventTypeCounts(@Payload Long count,
                                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                       @Header("kafka_receivedMessageKey") String eventType) {
        System.out.printf("Event type '%s' count: %d (partition: %d)%n", eventType, count, partition);
    }
}
