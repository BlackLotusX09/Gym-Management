package com.example.Gym.controller;

import com.example.Gym.model.User;
import com.example.Gym.model.Workout;
import com.example.Gym.repository.UserRepository;
import com.example.Gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getUserList(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}/workout")
    public ResponseEntity<?> getUserWorkout(@PathVariable String id) {
        // 1. Find the user first
        User user = userService.getUserById(id);

        // 2. Safety check: Does the user exist?
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // 3. Safety check: Does the user have a workout?
        if (user.getWorkout() == null) {
            return ResponseEntity.status(404).body("No workout assigned to this user yet");
        }

        // 4. Return the workout details
        return ResponseEntity.ok(user.getWorkout());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) throws IOException {
        if(user.getId()==null){
            return ResponseEntity.badRequest().body("ID cannot be found");
        }
        userService.saveUser(user);
        return ResponseEntity.status(201).body("User created successfully");
    }

    @PostMapping("/{id}/logs")
    public ResponseEntity<String> logUserWorkout(@PathVariable String id, @RequestBody Workout workout) {
        try {
            userService.logWorkout(id, workout);
            return ResponseEntity.status(201).body("Workout logged successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving log");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) throws IOException {
        if(user.getId()==null || user.getId().isEmpty()){
            return ResponseEntity.status(404).body("ID cannot be found");
        }
        userService.updateUser(user);
        return ResponseEntity.status(200).body("User updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) throws IOException {
        if(userService.getUserById(id)==null){
            return ResponseEntity.status(404).body("User not found for deletion");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted Successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if(user==null){
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

}
