package com.example.Gym.controller;

import com.example.Gym.model.HealthAttributes;
import com.example.Gym.service.HealthAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/health")
public class HealthAttributesController {

    @Autowired
    private HealthAttributesService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        HealthAttributes h = service.getById(id);

        if (h == null)
            return ResponseEntity.status(404).body("Profile not found");

        return ResponseEntity.ok(h);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> create(@PathVariable String id, @RequestBody HealthAttributes h) {
        try {
            service.save(id, h);
            return ResponseEntity.status(201).body("Health profile created");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody HealthAttributes h) {
        if (service.getById(id) == null)
            return ResponseEntity.status(404).body("Profile not found for update");

        try {
            service.update(id, h);
            return ResponseEntity.ok("Updated");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error updating");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (service.getById(id) == null)
            return ResponseEntity.status(404).body("Profile not found");

        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error deleting");
        }
    }
}
