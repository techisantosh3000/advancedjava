package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class UserEvent {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("value")
    private Double value;

    // Constructors
    public UserEvent() {}

    public UserEvent(String userId, String eventType, LocalDateTime timestamp, Double value) {
        this.userId = userId;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.value = value;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    @Override
    public String toString() {
        return "UserEvent{userId='" + userId + "', eventType='" + eventType +
                "', timestamp=" + timestamp + ", value=" + value + "}";
    }
}
