package com.example.Gym.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietSchedule {
    private String scheduleId;
    private String userId;
    private LocalDate date;
    private List<DietRow> rows; // I renamed this from 'row' to 'rows' to be grammatically correct
}