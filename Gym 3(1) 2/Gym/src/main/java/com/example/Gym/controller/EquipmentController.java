package com.example.Gym.controller;

import com.example.Gym.model.Equipment;
import com.example.Gym.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAllEquipment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Equipment eq = service.getEquipmentById(id);

        if (eq == null)
            return ResponseEntity.status(404).body("Equipment not found");

        return ResponseEntity.ok(eq);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Equipment eq) {
        try {
            service.saveEquipment(eq);
            return ResponseEntity.status(201).body("Equipment created");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving equipment");
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Equipment eq) {
        if (service.getEquipmentById(eq.getID()) == null)
            return ResponseEntity.status(404).body("Equipment not found for update");

        try {
            service.updateEquipment(eq);
            return ResponseEntity.ok("Equipment updated");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error updating");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (service.getEquipmentById(id) == null)
            return ResponseEntity.status(404).body("Equipment not found");

        try {
            service.deleteEquipment(id);
            return ResponseEntity.ok("Equipment deleted");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error deleting");
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable String id,
            @RequestParam boolean available) {

        try {
            // Call the service to flip the switch
            service.updateAvailability(id, available);

            // Return a helpful message based on what we just did
            String statusMessage = available ? "marked as AVAILABLE" : "marked as UNDER MAINTENANCE";
            return ResponseEntity.ok("Equipment " + statusMessage);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving status");
        } catch (RuntimeException e) {
            // Catches "Equipment not found" from the service
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
