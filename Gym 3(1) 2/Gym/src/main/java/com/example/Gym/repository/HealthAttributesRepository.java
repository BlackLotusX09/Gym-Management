package com.example.Gym.repository;

import com.example.Gym.model.HealthAttributes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HealthAttributesRepository {

    private static final String FILE_PATH = "src/main/resources/data/health.json";
    private ObjectMapper mapper = new ObjectMapper();

    public List<HealthAttributes> getAllHealthProfiles() {
        try {
            return mapper.readValue(
                    new File(FILE_PATH),
                    new TypeReference<List<HealthAttributes>>() {}
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void saveHealthProfile(HealthAttributes health) throws IOException {
        List<HealthAttributes> list = getAllHealthProfiles();
        list.add(health);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
    }

    public HealthAttributes getHealthById(String id) {
        return getAllHealthProfiles()
                .stream()
                .filter(h -> String.valueOf(h.getAge()).equals(id)) // FIX BELOW
                .findFirst()
                .orElse(null);
    }

    public void updateHealthProfile(String id, HealthAttributes updated) throws IOException {
        List<HealthAttributes> list = getAllHealthProfiles();

        for (int i = 0; i < list.size(); i++) {
            if (String.valueOf(list.get(i).getAge()).equals(id)) {  // FIX BELOW
                list.set(i, updated);
                break;
            }
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
    }

    public void deleteHealthProfile(String id) throws IOException {
        List<HealthAttributes> list = getAllHealthProfiles();
        list.removeIf(h -> String.valueOf(h.getAge()).equals(id)); // FIX BELOW
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
    }
}
