package com.example.kafka.streaming.util;

import com.example.kafka.streaming.constants.CommonConstants;
import com.example.kafka.streaming.exception.AccountBalancePubNonRetriableException;
import com.example.kafka.streaming.model.AccountBalanceEvent;
import com.example.kafka.streaming.model.AccountMetadata;
import com.example.kafka.streaming.model.BalanceEvent;
import com.example.kafka.streaming.model.Tuple4KStreamDLQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// ============================================
// 7. Utility Classes
// ============================================
@Component
@Slf4j
public class AccountBalancePublishingUtil {

    public static boolean isRetriable(Tuple4KStreamDLQ<?, ?> errorTuple) {
        Exception exception = errorTuple.getException();

        return CommonConstants.NON_RETRIABLE_EXCEPTIONS.stream()
                .noneMatch(nonRetriableClass -> nonRetriableClass.isInstance(exception));
    }

    public static AccountBalanceEvent transformToAccountBalanceEvent(BalanceEvent balanceEvent, AccountMetadata metadata) {
        try {
            Double balance = Double.parseDouble(balanceEvent.getAmount());

            return new AccountBalanceEvent(
                    balanceEvent.getAccountId(),
                    balance,
                    balanceEvent.getCurrency(),
                    metadata.getProductType(),
                    metadata.getAccountType(),
                    balanceEvent.getTimestamp(),
                    balanceEvent.getEventId()
            );
        } catch (NumberFormatException e) {
            throw new AccountBalancePubNonRetriableException("Invalid amount format: " + balanceEvent.getAmount(), e);
        }
    }

    public static void raiseAnException(String message) {
        throw new RuntimeException("Forced exception for testing: " + message);
    }
}
