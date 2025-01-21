package com.example.dto;

import java.util.List;

public class MenuResponseDTO {
    private List<String> menus; // List of generated menus

    // Constructor
    public MenuResponseDTO(List<String> menus) {
        this.menus = menus;
    }

    // Getters and Setters
    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }
}
