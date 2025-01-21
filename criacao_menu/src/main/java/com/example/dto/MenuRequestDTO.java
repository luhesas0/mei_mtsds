package com.example.dto;

public class MenuRequestDTO {
    private int days; // Number of days for the menu
    private String type; // Menu type: "meat", "vegetarian", or "fish"

    // Getters and Setters
    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
