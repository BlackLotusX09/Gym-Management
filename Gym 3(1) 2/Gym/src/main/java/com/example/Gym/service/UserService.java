package com.example.Gym.service;

import com.example.Gym.model.User;
import com.example.Gym.repository.UserRepository;
import com.example.Gym.model.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection (BEST PRACTICE)
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public  User getUserById(String id) {
        return userRepository.getUserById(id);
    }

    public void saveUser(User user) throws IOException {
        userRepository.saveUser(user);
    }

    public void updateUser(User user) throws IOException {
        userRepository.updateUser(user);
    }

    public void deleteUser(String id) throws IOException {
        userRepository.deleteUser(id);
    }



    public void logWorkout(String userId, Workout completedWorkout) throws IOException {
        User user = getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Safety: Create list if missing
        if (user.getWorkoutHistory() == null) {
            user.setWorkoutHistory(new ArrayList<>());
        }

        // --- AUTOMATION ---
        // We reuse the Workout object, but we Stamp it with "Today"
        LocalDate today = LocalDate.now();
        completedWorkout.setDate(today.toString());
        // ------------------

        // Add to history
        user.getWorkoutHistory().add(completedWorkout);

        // Save
        userRepository.updateUser(user);
    }
}
