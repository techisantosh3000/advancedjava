package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserProfile {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("lastUpdated")
    private LocalDateTime lastUpdated;

    // Constructors
    public UserProfile() {}

    public UserProfile(String userId, String name, String email, LocalDateTime lastUpdated) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    @Override
    public String toString() {
        return "UserProfile{userId='" + userId + "', name='" + name +
                "', email='" + email + "', lastUpdated=" + lastUpdated + "}";
    }
}
