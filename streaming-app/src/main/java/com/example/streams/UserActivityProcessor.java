package com.example.streams;

import com.example.model.UserEvent;
import com.example.model.UserProfile;
import com.example.model.UserActivitySummary;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class UserActivityProcessor {

    @Autowired
    public void processUserActivity(StreamsBuilder streamsBuilder) {

        // Create Serdes for JSON serialization/deserialization
        JsonSerde<UserEvent> userEventSerde = new JsonSerde<>(UserEvent.class);
        JsonSerde<UserProfile> userProfileSerde = new JsonSerde<>(UserProfile.class);
        JsonSerde<UserActivitySummary> userActivitySummarySerde = new JsonSerde<>(UserActivitySummary.class);

        // Create KTable from user profiles topic (compacted topic)
        KTable<String, UserProfile> userProfilesTable = streamsBuilder
                .table("user-profiles",
                        Consumed.with(Serdes.String(), userProfileSerde));

        // Create KStream from user events topic
        KStream<String, UserEvent> userEventsStream = streamsBuilder
                .stream("user-events",
                        Consumed.with(Serdes.String(), userEventSerde));

        // Aggregate user events by user ID to create activity summary
        KTable<String, UserActivitySummary> userActivityTable = userEventsStream
                .groupByKey(Grouped.with(Serdes.String(), userEventSerde))
                .aggregate(
                        // Initializer
                        () -> new UserActivitySummary("", 0L, 0.0, null),

                        // Aggregator
                        (key, event, aggregate) -> {
                            aggregate.setUserId(key);
                            aggregate.setTotalEvents(aggregate.getTotalEvents() + 1);
                            aggregate.setTotalValue(aggregate.getTotalValue() + event.getValue());
                            aggregate.setLastEventTimestamp(event.getTimestamp());
                            return aggregate;
                        },

                        // Materialized store configuration
                        Materialized.<String, UserActivitySummary, KeyValueStore<org.apache.kafka.common.util.Bytes, byte[]>>as("user-activity-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(userActivitySummarySerde)
                );

        // Join user activity with user profiles
        KTable<String, String> enrichedUserActivity = userActivityTable
                .join(userProfilesTable,
                        (activity, profile) -> {
                            return String.format(
                                    "User: %s (%s) - Events: %d, Total Value: %.2f, Last Activity: %s",
                                    profile.getName(),
                                    profile.getEmail(),
                                    activity.getTotalEvents(),
                                    activity.getTotalValue(),
                                    activity.getLastEventTimestamp()
                            );
                        });

        // Output enriched data to a new topic
        enrichedUserActivity.toStream().to("enriched-user-activity",
                Produced.with(Serdes.String(), Serdes.String()));

        // Filter high-value events and output to separate topic
        userEventsStream
                .filter((key, event) -> event.getValue() > 50.0)
                .to("high-value-events",
                        Produced.with(Serdes.String(), userEventSerde));

        // Group events by event type and count
        KTable<String, Long> eventTypeCounts = userEventsStream
                .map((key, event) -> KeyValue.pair(event.getEventType(), event))
                .groupByKey(Grouped.with(Serdes.String(), userEventSerde))
                .count(Materialized.as("event-type-counts"));

        // Output event type counts
        eventTypeCounts.toStream().to("event-type-counts",
                Produced.with(Serdes.String(), Serdes.Long()));

        // Print topology for debugging
        System.out.println("Kafka Streams Topology:");
        System.out.println(streamsBuilder.build().describe());
    }
}