package com.example.Gym.model;

import java.util.ArrayList;

public class GymManagement {
    private String name;
    ArrayList<User> users;
    ArrayList<Coach> coahes;
    ArrayList<Equipment> equipments;

    public GymManagement(String name,
                         ArrayList<User> users,
                         ArrayList<Coach> coahes,
                         ArrayList<Equipment> equipments) {
        this.name = name;
        this.users = users;
        this.coahes = coahes;
        this.equipments = equipments;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setCoahes(ArrayList<Coach> coahes) {
        this.coahes = coahes;
    }

    public void setEquipments(ArrayList<Equipment> equipments) {
        this.equipments = equipments;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Coach> getCoahes() {
        return coahes;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
