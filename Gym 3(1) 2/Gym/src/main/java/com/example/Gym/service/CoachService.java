
package com.example.Gym.service;

import com.example.Gym.model.Coach;
import com.example.Gym.model.User;
import com.example.Gym.model.Workout;
import com.example.Gym.repository.CoachRepository;
import com.example.Gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CoachService {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;

    @Autowired
    public CoachService(CoachRepository coachRepository, UserRepository userRepository) {
        this.coachRepository = coachRepository;
        this.userRepository = userRepository;
    }

    public List<Coach> getAllCoaches() {
        return coachRepository.getAllCoaches();
    }

    public void assignUser(String coachId, String userId) throws IOException {

        Coach coach = getCoachById(coachId);
        User user = userRepository.getUserById(userId);

        if (coach != null && user != null) {
            // 3. Modify the object
            coach.addUser(user); // Assuming Coach has getMembers()

            // 4. CRITICAL STEP: Save the changes back to the file!
            coachRepository.updateCoach(coach);
        }
    }

    public void assignWorkout(String coachId, String userId, Workout workout) throws IOException {
        // 1. Get the Coach
        Coach coach = getCoachById(coachId);
        if (coach == null) {
            throw new RuntimeException("Coach not found with ID: " + coachId);
        }

        // 2. CHECK: Is this user actually in the Coach's list?
        User targetUser = null;

        // We loop through the coach's "members" list to find the user
        for (User u : coach.getAssignedUsers()) {
            if (u.getId().equals(userId)) {
                targetUser = u;
                break; // Found them!
            }
        }

        // 3. If the loop finished and targetUser is still null, they aren't a student of this coach
        if (targetUser == null) {
            throw new RuntimeException("Access Denied: This user is not assigned to Coach " + coach.getName());
        }

        // 4. Assign the workout
        targetUser.setWorkout(workout);

        // 5. CRITICAL: Data Synchronization
        // Because we are using files (not a real DB), the 'User' object exists in two places:
        // Place A: Inside the 'users.json' file (The master record)
        // Place B: Inside the 'coaches.json' file (The coach's copy of the user)

        // We must update BOTH to keep them in sync:

        // Update the Global User Repository (so the User sees it when they log in)
        userRepository.updateUser(targetUser);

        // Update the Coach Repository (so the Coach sees it in their list)
        coachRepository.updateCoach(coach);
    }






    public Coach getCoachById(String id) {
        return coachRepository.getCoachById(id);
    }

    public void saveCoach(Coach coach) throws IOException {
        coachRepository.saveCoach(coach);
    }

    public void updateCoach(Coach coach) throws IOException {
        coachRepository.updateCoach(coach);
    }

    public void deleteCoach(String id) throws IOException {
        coachRepository.deleteCoach(id);
    }
}
