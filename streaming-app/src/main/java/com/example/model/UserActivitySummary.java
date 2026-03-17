package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserActivitySummary {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("totalEvents")
    private Long totalEvents;

    @JsonProperty("totalValue")
    private Double totalValue;

    @JsonProperty("lastEventTimestamp")
    private LocalDateTime lastEventTimestamp;

    // Constructors
    public UserActivitySummary() {}

    public UserActivitySummary(String userId, Long totalEvents, Double totalValue, LocalDateTime lastEventTimestamp) {
        this.userId = userId;
        this.totalEvents = totalEvents;
        this.totalValue = totalValue;
        this.lastEventTimestamp = lastEventTimestamp;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Long getTotalEvents() { return totalEvents; }
    public void setTotalEvents(Long totalEvents) { this.totalEvents = totalEvents; }

    public Double getTotalValue() { return totalValue; }
    public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }

    public LocalDateTime getLastEventTimestamp() { return lastEventTimestamp; }
    public void setLastEventTimestamp(LocalDateTime lastEventTimestamp) { this.lastEventTimestamp = lastEventTimestamp; }

    @Override
    public String toString() {
        return "UserActivitySummary{userId='" + userId + "', totalEvents=" + totalEvents +
                ", totalValue=" + totalValue + ", lastEventTimestamp=" + lastEventTimestamp + "}";
    }
}