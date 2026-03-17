package com.example.controller;


import com.example.model.UserEvent;
import com.example.model.UserProfile;
import com.example.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/kafka")
public class KafkaTestController {

    @Autowired
    private KafkaProducerService producerService;

    @PostMapping("/send-event")
    public String sendUserEvent(@RequestBody UserEvent event) {
        if (event.getTimestamp() == null) {
            event.setTimestamp(LocalDateTime.now());
        }
        producerService.sendUserEvent(event);
        return "Event sent successfully";
    }

    @PostMapping("/send-profile")
    public String sendUserProfile(@RequestBody UserProfile profile) {
        if (profile.getLastUpdated() == null) {
            profile.setLastUpdated(LocalDateTime.now());
        }
        producerService.sendUserProfile(profile);
        return "Profile sent successfully";
    }

    @PostMapping("/generate-sample-data")
    public String generateSampleData() {
        producerService.generateSampleData();
        return "Sample data generated successfully";
    }
}
