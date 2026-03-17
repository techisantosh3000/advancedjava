package com.example.kafka.streaming.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountMetadata {
    private String accountId;
    private String productType;
    private String accountType;
    private String status;
    private Long lastUpdated;
}
