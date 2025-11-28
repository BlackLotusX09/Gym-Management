package com.example.Gym.repository;

import com.example.Gym.model.Equipment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EquipmentRepository {

    private static final String FILE_PATH = "src/main/resources/data/equipment.json";
    private ObjectMapper mapper = new ObjectMapper();

    public List<Equipment> getAllEquipment() {
        try {
            return mapper.readValue(
                    new File(FILE_PATH),
                    new TypeReference<List<Equipment>>() {}
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void saveEquipment(Equipment equipment) throws IOException {
        // 1. Get the current list (returns empty list if file is missing)
        List<Equipment> list = getAllEquipment();
        list.add(equipment);

        File file = new File(FILE_PATH);

        // --- NEW SELF-HEALING BLOCK ---
        // If the file doesn't exist, we force Java to create it.
        if (!file.exists()) {
            // Step A: Create the 'data' folder if it's missing
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            // Step B: Create the empty 'equipment.json' file
            file.createNewFile();
        }
        // ------------------------------

        // 2. Save the data
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
    }

    public Equipment getEquipmentById(String id) {
        return getAllEquipment()
                .stream()
                .filter(e -> e.getID().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateEquipment(Equipment updated) throws IOException {
        List<Equipment> list = getAllEquipment();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getID().equals(updated.getID())) {
                list.set(i, updated);
                break;
            }
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
    }



    public void deleteEquipment(String id) throws IOException {
        List<Equipment> list = getAllEquipment();
        list.removeIf(e -> e.getID().equals(id));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
    }
}
