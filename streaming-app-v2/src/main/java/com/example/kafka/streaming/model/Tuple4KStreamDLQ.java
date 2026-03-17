package com.example.kafka.streaming.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ============================================
// 4. Error Context Classes
// ============================================
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tuple4KStreamDLQ<T, U> {
    private Exception exception;
    private T processedMessage;
    private U originalMessage;
    private Integer retryCount;

    public static <T, U> Tuple4KStreamDLQ<T, U> ofError(Exception exception, T processed, U original, Integer retryCount) {
        return new Tuple4KStreamDLQ<>(exception, processed, original, retryCount);
    }
}
