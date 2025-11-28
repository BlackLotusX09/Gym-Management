package com.example.Gym.repository;

import com.example.Gym.model.Coach;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CoachRepository {

    private static final String FILE_PATH = "src/main/resources/data/coaches.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Coach> getAllCoaches() {
        try {
            return mapper.readValue(
                    new File(FILE_PATH),
                    new TypeReference<List<Coach>>() {}
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Coach getCoachById(String id) {
        return getAllCoaches()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void saveCoach(Coach coach) throws IOException {
        List<Coach> coaches = getAllCoaches();
        coaches.add(coach);

        // --- SELF-HEALING CODE ---
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs(); // Create 'data' folder
            }
            file.createNewFile(); // Create 'coaches.json'
        }
        // -------------------------

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, coaches);
    }

    public void updateCoach(Coach updatedCoach) throws IOException {
        List<Coach> coaches = getAllCoaches();
        for (int i = 0; i < coaches.size(); i++) {
            if (coaches.get(i).getId().equals(updatedCoach.getId())) {
                coaches.set(i, updatedCoach);
                break;
            }
        }

        // --- SELF-HEALING CODE ---
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        // -------------------------

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, coaches);
    }

    public void deleteCoach(String id) throws IOException {
        List<Coach> coaches = getAllCoaches();
        coaches.removeIf(c -> c.getId().equals(id));
        mapper.writeValue(new File(FILE_PATH), coaches);
    }
}
