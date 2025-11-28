package com.example.Gym.service;

import com.example.Gym.model.Equipment;
import com.example.Gym.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository repo;

    public List<Equipment> getAllEquipment() {
        return repo.getAllEquipment();
    }

    public Equipment getEquipmentById(String id) {
        return repo.getEquipmentById(id);
    }

    public void saveEquipment(Equipment eq) throws IOException {
        repo.saveEquipment(eq);
    }

    public void updateEquipment(Equipment eq) throws IOException {
        repo.updateEquipment(eq);
    }

    public void deleteEquipment(String id) throws IOException {
        repo.deleteEquipment(id);
    }

    // Add this to EquipmentService.java
    public void updateAvailability(String id, boolean isAvailable) throws IOException {
        // 1. Find the equipment
        Equipment eq = getEquipmentById(id);
        if (eq == null) {
            throw new RuntimeException("Equipment not found");
        }

        // 2. Change just the availability
        eq.setAvailability(isAvailable);

        // 3. Save it back to the file
        // (This assumes you have an updateEquipment method in your Repository)
        repo.updateEquipment(eq);
    }
}
