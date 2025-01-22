package com.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuRequestDTO {
    private int days; // Número de dias para o menu
    private String type; // Tipo de menu: "meat", "vegetarian", "fish"
    private List<String> mainDishes;
    private List<String> soups;
    private List<String> desserts;
    private List<String> snacks;
}
