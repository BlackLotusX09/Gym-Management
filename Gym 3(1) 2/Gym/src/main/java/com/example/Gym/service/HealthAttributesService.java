package com.example.Gym.service;

import com.example.Gym.model.HealthAttributes;
import com.example.Gym.model.User;
import com.example.Gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthAttributesService {

    @Autowired
    private UserRepository userRepository;

    // 1. GET BY ID (Already existed)
    public HealthAttributes getById(String userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) return null;
        return user.getHealthAttributes();
    }

    // 2. GET ALL (New - Extracts health stats from ALL users)
    public List<HealthAttributes> getAll() {
        return userRepository.getAllUsers().stream()
                .map(User::getHealthAttributes) // Extract the health part
                .filter(h -> h != null)         // Ignore users who haven't set it yet
                .collect(Collectors.toList());
    }

    // 3. SAVE / CREATE (New)
    public void save(String userId, HealthAttributes healthAttributes) throws IOException {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        // Link the health stats to the user
        user.setHealthAttributes(healthAttributes);

        // CRITICAL: Save the User to persist the change
        userRepository.updateUser(user);
    }

    // 4. UPDATE (New - Logic is same as save because it overwrites)
    public void update(String userId, HealthAttributes healthAttributes) throws IOException {
        save(userId, healthAttributes);
    }

    // 5. DELETE (New)
    public void delete(String userId) throws IOException {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Remove the health stats
        user.setHealthAttributes(null);

        // Save the User
        userRepository.updateUser(user);
    }
}