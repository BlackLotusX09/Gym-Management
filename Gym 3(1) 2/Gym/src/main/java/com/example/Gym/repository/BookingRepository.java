package com.example.Gym.repository;

import com.example.Gym.model.Booking;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {

    private static final String FILE_PATH = "src/main/resources/data/bookings.json";
    private ObjectMapper mapper = new ObjectMapper();

    public List<Booking> getAllBookings() {
        try {
            return mapper.readValue(new File(FILE_PATH), new TypeReference<List<Booking>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void saveBooking(Booking booking) throws IOException {
        List<Booking> list = getAllBookings();
        list.add(booking);

        // Self-healing file creation
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            file.createNewFile();
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
    }
}