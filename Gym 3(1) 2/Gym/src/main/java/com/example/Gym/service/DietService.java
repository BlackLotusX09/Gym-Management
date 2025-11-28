package com.example.Gym.service;

import com.example.Gym.model.DietRow;
import com.example.Gym.model.DietSchedule;
import com.example.Gym.model.HealthAttributes;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DietService {

    public DietSchedule generateZigZag(String userId, HealthAttributes h) {

        // 1. Calculate BMR (Basal Metabolic Rate)
        double BMR;

        // CHECK: Make sure your HealthAttributes model has 'getGender', 'getWeight', etc.
        // If your model uses 'sex' instead of 'gender', change it here!
        if (h.getGender() != null && h.getGender().equalsIgnoreCase("male")) {
            BMR = 10 * h.getWeight() + 6.25 * h.getHeight() - 5 * h.getAge() + 5;
        } else {
            BMR = 10 * h.getWeight() + 6.25 * h.getHeight() - 5 * h.getAge() - 161;
        }

        // 2. Calculate TDEE (Total Daily Energy Expenditure)
        // Assuming "Lightly Active" (1.375) as default for now
        double TDEE = BMR * 1.375;

        // 3. Define Calorie Deficits
        double mild = TDEE - 250;
        double loss = TDEE - 500;
        double extreme = TDEE - 700;

        // 4. Create the Zig-Zag Cycle (High days vs Low days)
        String[] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        // We boost calories on Sunday and Friday (Cheats/Refeeds)
        int[] boost = {350, 0, 0, 0, 0, 0, 350};

        List<DietRow> rows = new ArrayList<>();
        for(int i=0; i<7; i++){
            rows.add(new DietRow(
                    days[i],
                    fmt(mild + boost[i]),
                    fmt(loss + boost[i]),
                    fmt(extreme + boost[i])
            ));
        }

        // 5. Build the Schedule Object
        String scheduleId = "DS-" + System.currentTimeMillis();
        return new DietSchedule(scheduleId, userId, LocalDate.now(), rows);
    }

    // Helper to format numbers nicely (removes decimals)
    private String fmt(double value){
        return String.format("%.0f Calories", value);
    }
}