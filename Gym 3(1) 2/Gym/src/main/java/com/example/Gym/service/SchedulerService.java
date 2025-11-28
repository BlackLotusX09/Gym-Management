package com.example.Gym.service;

import com.example.Gym.model.Booking;
import com.example.Gym.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class SchedulerService {

    @Autowired
    private BookingRepository bookingRepository;

    // We use 'synchronized' to handle Concurrency (The "Real World" Locking)
    // This prevents two users from booking the exact same millisecond
    public synchronized void bookSession(String userId, String coachId, String date, int hour) throws IOException {

        // 1. Load all existing bookings
        List<Booking> allBookings = bookingRepository.getAllBookings();

        // 2. RUN THE CONSTRAINT ALGORITHM
        for (Booking b : allBookings) {

            // Check if the DATE and HOUR match
            if (b.getDate().equals(date) && b.getHour() == hour) {

                // Constraint A: Is the COACH already busy?
                if (b.getCoachId().equals(coachId)) {
                    throw new RuntimeException("Coach is already booked at this time!");
                }

                // Constraint B: Is the USER already doing something else?
                if (b.getUserId().equals(userId)) {
                    throw new RuntimeException("User already has a booking at this time!");
                }
            }
        }

        // 3. If loop finishes with no errors, the slot is free!
        Booking newBooking = new Booking();
        newBooking.setBookingId(UUID.randomUUID().toString()); // Generate unique ID
        newBooking.setUserId(userId);
        newBooking.setCoachId(coachId);
        newBooking.setDate(date);
        newBooking.setHour(hour);

        // 4. Save to file
        bookingRepository.saveBooking(newBooking);
    }

    // Helper to see a schedule
    public List<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }
}