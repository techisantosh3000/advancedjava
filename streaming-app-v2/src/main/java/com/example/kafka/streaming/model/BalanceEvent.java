package com.example.kafka.streaming.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ============================================
// 3. Domain Models
// ============================================
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceEvent {
    private String accountId;
    private String amount;
    private String currency;
    private String transactionType;
    private Long timestamp;
    private String eventId;
}
