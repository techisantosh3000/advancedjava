package com.example.kafka.streaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

// ============================================
// 1. Main Application Class
// ============================================
@SpringBootApplication
@EnableKafkaStreams
public class AccountBalanceStreamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountBalanceStreamingApplication.class, args);
    }
}