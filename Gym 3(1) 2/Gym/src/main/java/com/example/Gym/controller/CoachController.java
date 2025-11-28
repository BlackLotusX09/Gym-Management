package com.example.Gym.controller;

import com.example.Gym.model.Coach;
import com.example.Gym.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Gym.model.Workout;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping
    public ResponseEntity<List<Coach>> getCoachList() {
        return ResponseEntity.ok(coachService.getAllCoaches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoachById(@PathVariable String id) {
        Coach coach = coachService.getCoachById(id);
        if (coach == null) {
            return ResponseEntity.status(404).body("Coach not found");
        }
        return ResponseEntity.ok(coach);
    }

    @PostMapping
    public ResponseEntity<String> createCoach(@RequestBody Coach coach) {
        try {
            coachService.saveCoach(coach);
            return ResponseEntity.status(201).body("Coach created successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving coach");
        }
    }

    @PutMapping()
    public ResponseEntity<String> updateCoach(@RequestBody Coach coach) {
        if (coachService.getCoachById(coach.getId()) == null) {
            return ResponseEntity.status(404).body("Coach not found for update");
        }
        try {
            coachService.updateCoach(coach);
            return ResponseEntity.ok("Coach updated successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error updating coach");
        }
    }

    @PutMapping("/{coachId}/users/{userId}/workouts")
    public ResponseEntity<String> reassignWorkoutToUser(
            @PathVariable String coachId,
            @PathVariable String userId,
            @RequestBody Workout newWorkout) {
        try {
            // We reuse the existing logic because "Re-assigning"
            // is technically the same as "Assigning" (it overwrites the old one).
            coachService.assignWorkout(coachId, userId, newWorkout);

            return ResponseEntity.ok("Workout re-assigned (updated) successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error updating workout data");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{coachId}/users/{userId}")
    public ResponseEntity<String> assignUserToCoach(@PathVariable String coachId, @PathVariable String userId) {
        try {
            coachService.assignUser(coachId, userId);
            return ResponseEntity.ok("User assigned to coach successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error accessing file data");
        } catch (RuntimeException e) {
            // This catches "Coach not found" or "User not found" errors from your Service
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/{coachId}/users/{userId}/workouts")
    public ResponseEntity<String> assignWorkoutToUser(
            @PathVariable String coachId,
            @PathVariable String userId,
            @RequestBody Workout workout) {
        try {
            coachService.assignWorkout(coachId, userId, workout);
            return ResponseEntity.ok("Workout assigned to user successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving workout data");
        } catch (RuntimeException e) {
            // This catches "Access Denied" or "User not found" errors
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoach(@PathVariable String id) {
        if (coachService.getCoachById(id) == null) {
            return ResponseEntity.status(404).body("Coach not found for deletion");
        }
        try {
            coachService.deleteCoach(id);
            return ResponseEntity.ok("Coach deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error deleting coach");
        }
    }
}
