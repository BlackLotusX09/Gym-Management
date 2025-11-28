package com.example.Gym.controller;

import com.example.Gym.model.Booking;
import com.example.Gym.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking bookingRequest) {
        try {
            // We expect the user to send userId, coachId, date, and hour
            schedulerService.bookSession(
                    bookingRequest.getUserId(),
                    bookingRequest.getCoachId(),
                    bookingRequest.getDate(),
                    bookingRequest.getHour()
            );
            return ResponseEntity.status(201).body("Session booked successfully!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("System Error: Could not save booking.");
        } catch (RuntimeException e) {
            // This catches our "Coach is already booked" error
            return ResponseEntity.badRequest().body("Booking Failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Booking>> viewAllBookings() {
        return ResponseEntity.ok(schedulerService.getAllBookings());
    }
}