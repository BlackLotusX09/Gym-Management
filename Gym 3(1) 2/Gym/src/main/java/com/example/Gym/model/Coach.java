package com.example.Gym.model;
import com.example.Gym.model.User;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
public class Coach {
    private String id;
    private String name;
    private String specialization;
    private int experienceYears;
    private List<User> assignedUsers = new ArrayList<>();
//    public Coach() {}
//
//    public String getId() { return id; }
//    public void setId(String id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getSpecialization() { return specialization; }
//    public void setSpecialization(String specialization) { this.specialization = specialization; }
//
//    public int getExperienceYears() { return experienceYears; }
//    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

    public void addUser(User user){
        this.assignedUsers.add(user);
    }
    public void removeUser(User user){
        this.assignedUsers.remove(user);
    }
}
