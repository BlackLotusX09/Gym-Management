package com.example.Gym.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietRow {
    private String day;
    private String mild;    // e.g. "2000 Calories"
    private String loss;    // e.g. "1800 Calories"
    private String extreme; // e.g. "1500 Calories"
}