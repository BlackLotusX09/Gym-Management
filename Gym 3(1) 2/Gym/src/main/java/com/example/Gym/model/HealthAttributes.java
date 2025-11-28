package com.example.Gym.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthAttributes {

    private String id; // Lowercase 'id' is good! Matches JSON standard.

    private int age;
    private int height; // in cm
    private int weight; // in kg
    private String gender; // "male" or "female"

    // Extra tracking fields (Optional for Diet, but good for the app)
    private int dailySteps;
    private double caloriesBurnedToday;
    private int activeMinutes;
}