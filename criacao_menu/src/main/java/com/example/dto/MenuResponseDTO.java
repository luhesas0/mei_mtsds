package com.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuResponseDTO {
    private Long id;
    private String type;
    private int days;
    private List<String> mainDishes;
    private List<String> soups;
    private List<String> desserts;
    private List<String> snacks;

    public MenuResponseDTO(Long id, String type, int days, List<String> mainDishes, List<String> soups,
                           List<String> desserts, List<String> snacks) {
        this.id = id;
        this.type = type;
        this.days = days;
        this.mainDishes = mainDishes;
        this.soups = soups;
        this.desserts = desserts;
        this.snacks = snacks;
    }
}
