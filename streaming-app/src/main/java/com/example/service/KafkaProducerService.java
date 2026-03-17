package com.example.service;

import com.example.model.UserEvent;
import com.example.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class KafkaProducerService {

    private static final String USER_EVENTS_TOPIC = "user-events";
    private static final String USER_PROFILES_TOPIC = "user-profiles";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserEvent(UserEvent userEvent) {
        kafkaTemplate.send(USER_EVENTS_TOPIC, userEvent.getUserId(), userEvent);
        System.out.println("Sent user event: " + userEvent);
    }

    public void sendUserProfile(UserProfile userProfile) {
        kafkaTemplate.send(USER_PROFILES_TOPIC, userProfile.getUserId(), userProfile);
        System.out.println("Sent user profile: " + userProfile);
    }

    // Helper method to generate sample data
    public void generateSampleData() {
        // Send user profiles
        sendUserProfile(new UserProfile("user1", "John Doe", "john@example.com", LocalDateTime.now()));
        sendUserProfile(new UserProfile("user2", "Jane Smith", "jane@example.com", LocalDateTime.now()));

        // Send user events
        sendUserEvent(new UserEvent("user1", "login", LocalDateTime.now(), 1.0));
        sendUserEvent(new UserEvent("user1", "purchase", LocalDateTime.now(), 99.99));
        sendUserEvent(new UserEvent("user2", "login", LocalDateTime.now(), 1.0));
        sendUserEvent(new UserEvent("user1", "view_product", LocalDateTime.now(), 0.0));
        sendUserEvent(new UserEvent("user2", "purchase", LocalDateTime.now(), 149.99));
    }
}