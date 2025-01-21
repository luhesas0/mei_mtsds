package com.example.service;

import com.example.dto.MenuRequestDTO;
import com.example.dto.MenuResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private RestTemplate restTemplate;

    private final String verificaStockUrl = "http://localhost:8083/api/stock";

    public MenuResponseDTO createMenu(MenuRequestDTO request) {
        List<String> menus = new ArrayList<>();

        for (int i = 0; i < request.getDays(); i++) {
            // Generate a menu for one day
            String menu = generateMenu(request.getType());
            menus.add(menu);
        }

        return new MenuResponseDTO(menus);
    }

    private String generateMenu(String type) {
        // Mock stock check (replace this with real verifica_stock logic)
        boolean enoughStock = checkStock();

        if (!enoughStock) {
            return "Not enough stock to generate menu.";
        }

        // Generate menu based on type
        if ("vegetarian".equalsIgnoreCase(type)) {
            return "Vegetarian menu: Soup, Salad, Vegetarian Main Dish, Dessert, Snack";
        } else if ("fish".equalsIgnoreCase(type)) {
            return "Fish menu: Fish Soup, Grilled Fish, Dessert, Snack";
        } else {
            return "Meat menu: Meat Soup, Steak, Dessert, Snack";
        }
    }

    private boolean checkStock() {
        // Example REST call to verifica_stock
        Boolean response = restTemplate.getForObject(verificaStockUrl + "/check", Boolean.class);
        return response != null && response;
    }
}
