package com.example.kafka.streaming.constants;

import com.example.kafka.streaming.exception.AccountBalancePubNonRetriableException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonConstants {

    public static final List<Class<? extends Exception>> NON_RETRIABLE_EXCEPTIONS = List.of(
            NullPointerException.class,
            ClassCastException.class,
            JsonParseException.class,
            JsonMappingException.class,
            JsonProcessingException.class,
            SerializationException.class,
            NumberFormatException.class,
            IllegalArgumentException.class,
            AccountBalancePubNonRetriableException.class
    );

    public static final String RETRY_COUNT_HEADER = "retryCount";
    public static final String ERROR_TYPE_HEADER = "errorType";
    public static final String ORIGINAL_TOPIC_HEADER = "originalTopic";
    public static final String EXCEPTION_MESSAGE_HEADER = "exceptionMessage";
}
