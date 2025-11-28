package com.example.Gym.model;
import com.example.Gym.model.Workout;
import com .example.Gym.model.Exercise;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private String id;
    private String name;
    private String membershipStatus;
    private HealthAttributes healthAttributes;
    private Workout workout;

    private List<Workout> workoutHistory = new ArrayList<>();
}
