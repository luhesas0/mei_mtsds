package com.example.service;

import com.example.dto.MenuRequestDTO;
import com.example.dto.MenuResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private StockClient stockClient;

    public MenuResponseDTO createMenu(MenuRequestDTO request) {
        List<String> menus = new ArrayList<>();

        for (int i = 0; i < request.getDays(); i++) {
            if (!stockClient.checkStock()) {
                return new MenuResponseDTO(null, "Stock insuficiente", 0, null, null, null, null);
            }

            String menu = generateMenu(request.getType());
            menus.add(menu);
        }

        return new MenuResponseDTO(null, request.getType(), request.getDays(), menus, null, null, null);
    }

    private String generateMenu(String type) {
        switch (type.toLowerCase()) {
            case "vegetarian":
                return "Vegetarian menu: Soup, Salad, Vegetarian Main Dish, Dessert, Snack";
            case "fish":
                return "Fish menu: Fish Soup, Grilled Fish, Dessert, Snack";
            case "meat":
                return "Meat menu: Meat Soup, Steak, Dessert, Snack";
            default:
                throw new IllegalArgumentException("Invalid menu type: " + type);
        }
    }
}
