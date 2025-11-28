package com.example.Gym.repository;

import com.example.Gym.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private static final String FILE_PATH = "src/main/resources/data/users.json";
    private  ObjectMapper mapper = new ObjectMapper();
    public  List<User> getAllUsers() {
        try {
            return mapper.readValue(
                    new File(FILE_PATH),
                    new TypeReference<List<User>>() {}
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

//    public void saveUser(User user) throws IOException {
//        List<User> users = getAllUsers();
//        users.add(user);
//        mapper.writeValue(new File(FILE_PATH), users);
//    }

    public  User getUserById(String id) {
        return getAllUsers()
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // In UserRepository.java

    public void saveUser(User user) throws IOException {
        List<User> users = getAllUsers();
        users.add(user);

        // --- SELF-HEALING CODE ---
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        // -------------------------

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
    }

    public void updateUser(User updatedUser) throws IOException {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
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

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
    }

    public void deleteUser(String id) throws IOException {
        List<User> users = getAllUsers();
        users.removeIf(u -> u.getId().equals(id));
        mapper.writeValue(new File(FILE_PATH), users);
    }
}
