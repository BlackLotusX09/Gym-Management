package com.example.Gym.controller;

import com.example.Gym.model.DietSchedule;
import com.example.Gym.model.HealthAttributes;
import com.example.Gym.service.DietService;
import com.example.Gym.service.HealthAttributesService; // Make sure this matches your actual service name
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diet")
public class DietController {

    @Autowired
    private HealthAttributesService healthService;

    @Autowired
    private DietService dietService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getZigZagPlan(@PathVariable String userId) {

        // 1. Fetch health attributes of the user using YOUR existing service
        HealthAttributes health = healthService.getById(userId);

        if (health == null) {
            return ResponseEntity.status(404).body("Health attributes not found for user " + userId + ". Please update your profile first.");
        }

        // 2. Generate ZigZag schedule
        try {
            DietSchedule schedule = dietService.generateZigZag(userId, health);
            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error generating diet: " + e.getMessage());
        }
    }
}