package com.example.Gym.model;

public class HealthCalculator {

    public static double calculateBMR(double weight, double height, int age, String gender) {
        if (gender.equalsIgnoreCase("male")) {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

    public static double calculateTDEE(double bmr, double activityFactor) {
        return bmr * activityFactor;
    }

    public static double calculateWeeklyCalories(double tdee) {
        return tdee * 7;
    }
}


