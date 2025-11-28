package com.example.Gym.model;
import com.example.Gym.model.Equipment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    private Equipment equipment;
    private String name;
    private int sets;
    private int reps;
}
