package com.example.kafka.streaming.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

// ============================================
// 11. Serde Utilities
// ============================================
@Component
public class SerdeUtils {

    public static <T> Serde<T> createJsonSerde(Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonSerializer<T> serializer = new JsonSerializer<>(objectMapper);
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz, objectMapper);

        return Serdes.serdeFrom(serializer, deserializer);
    }

    // Add this method for TypeReference support
    public static <T> Serde<T> createJsonSerde(TypeReference<T> typeRef) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonSerializer<T> serializer = new JsonSerializer<>(objectMapper);
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(typeRef, objectMapper);

        return Serdes.serdeFrom(serializer, deserializer);
    }
}
