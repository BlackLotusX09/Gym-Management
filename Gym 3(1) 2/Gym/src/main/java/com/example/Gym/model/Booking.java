package com.example.Gym.model;

import lombok.Data;

@Data
public class Booking {
    private String bookingId;
    private String userId;
    private String coachId;
    private String date; // Format: "2025-11-22"
    private int hour;    // Format: 0-23 (e.g., 14 means 2:00 PM)
}