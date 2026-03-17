package com.example.kafka.streaming.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceEvent {
    private String accountId;
    private Double balance;
    private String currency;
    private String productType; // PSA or PBL
    private String accountType;
    private Long timestamp;
    private String eventId;
}
