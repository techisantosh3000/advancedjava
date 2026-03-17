package com.example.kafka.streaming;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;

/**
 * Step 1: Basic Kafka Streams Application
 *
 * This demonstrates the fundamental concepts:
 * - Stream reading from a topic
 * - Basic filtering
 * - Writing to another topic
 */
@SpringBootApplication
public class BasicKafkaStreamsApp {

    private KafkaStreams kafkaStreams;

    public static void main(String[] args) {
        SpringApplication.run(BasicKafkaStreamsApp.class, args);
    }

    @PostConstruct
    public void startStreams() {
        // Step 1: Configure Kafka Streams
        Properties config = getStreamsConfig();

        // Step 2: Build the stream topology
        StreamsBuilder builder = new StreamsBuilder();

        // Step 3: Define the stream processing logic
        buildStreamTopology(builder);

        // Step 4: Start the streams application
        kafkaStreams = new KafkaStreams(builder.build(), config);
        kafkaStreams.start();

        System.out.println("Kafka Streams application started successfully!");
    }

    private void buildStreamTopology(StreamsBuilder builder) {
        // Create a stream from input topic
        KStream<String, String> inputStream = builder.stream(
                "input-topic",
                Consumed.with(Serdes.String(), Serdes.String())
        );

        // Process the stream: filter and transform
        KStream<String, String> processedStream = inputStream
                // Filter: only process non-null values
                .filter((key, value) -> key != null && value != null)
                // Transform: add timestamp to each message
                .mapValues(value -> "Processed at " + System.currentTimeMillis() + ": " + value)
                // Peek: log what we're processing (debugging)
                .peek((key, value) -> System.out.println("Processing - Key: " + key + ", Value: " + value));

        // Send processed data to output topic
        processedStream.to(
                "output-topic",
                Produced.with(Serdes.String(), Serdes.String())
        );
    }

    private Properties getStreamsConfig() {
        Properties config = new Properties();

        // Required configuration
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "basic-streams-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Serialization configuration
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Processing guarantee (at-least-once vs exactly-once)
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);

        return config;
    }

    @PreDestroy
    public void stopStreams() {
        if (kafkaStreams != null) {
            kafkaStreams.close();
            System.out.println("Kafka Streams application stopped.");
        }
    }
}

/**
 * Key Kafka Streams Concepts Demonstrated:
 *
 * 1. **Stream**: An unbounded sequence of events (KStream)
 * 2. **Topology**: The processing logic graph (what operations to perform)
 * 3. **Source**: Where data comes from (input-topic)
 * 4. **Sink**: Where data goes to (output-topic)
 * 5. **Processor**: Operations on data (filter, map, etc.)
 * 6. **Serdes**: Serialization/Deserialization (how to convert data)
 *
 * This basic example shows:
 * - Reading from a Kafka topic
 * - Filtering null values
 * - Transforming data (adding timestamp)
 * - Writing to another Kafka topic
 */