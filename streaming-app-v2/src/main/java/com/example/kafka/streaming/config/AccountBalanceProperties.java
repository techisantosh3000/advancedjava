package com.example.kafka.streaming.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// ============================================
// 2. Configuration Properties
// ============================================
@ConfigurationProperties(prefix = "account.balance")
@Data
@Component
public class AccountBalanceProperties {

    private String inputTopic = "balance-events";
    private String outputPsaTopic = "account-balance-psa";
    private String outputPblTopic = "account-balance-pbl";
    private String retryTopic = "account-balance-retry";
    private String dlqTopic = "account-balance-dlq";
    private String poisonTopic = "account-balance-poison";
    private String accountMetadataTopic = "account-metadata";

    private int maxRetryCount = 5;
    private String bufferStoreName = "BUFFER_STORE";
    private String accountStateStoreName = "ACCOUNT_STATE_STORE";
    private String applicationId = "account-balance-publisher";
}
