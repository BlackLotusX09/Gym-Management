package com.example.Gym.model;

import com.example.Gym.model.Exercise;
import com.example.Gym.model.Equipment;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    private String id;
    private String type;
    private String description;
    private int durationInMinutes;

    private String date;

    private List<Exercise> exercises=new ArrayList<>();
}
