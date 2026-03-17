package com.example.kafka.streaming.exception;

// ============================================
// 5. Custom Exceptions
// ============================================
public class AccountBalancePubNonRetriableException extends RuntimeException {
    public AccountBalancePubNonRetriableException(String message) {
        super(message);
    }

    public AccountBalancePubNonRetriableException(String message, Throwable cause) {
        super(message, cause);
    }
}

