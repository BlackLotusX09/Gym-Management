package com.example.Gym.repository;

import com.example.Gym.model.Workout;
import com.example.Gym.model.Exercise;
import com.example.Gym.model.Equipment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkoutRepository {
    private static final String FILE_PATH = "src/main/resources/data/users.json";
    ObjectMapper mapper = new ObjectMapper();

    public List<Workout> getAllWorkouts() {
        try{
            return mapper.readValue(new File(FILE_PATH), new TypeReference<List<Workout>>(){});
        }catch (Exception e){
            return new ArrayList<>();

        }
    }


}
