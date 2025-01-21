package com.example.services;

import com.example.config.IngredientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IngredientValidator {

    @Autowired
    private IngredientConfig ingredientConfig;

    public boolean validateIngredients(List<String> ingredients) {
        Map<String, List<String>> validIngredients = ingredientConfig.getIngredientes();
        for (String ingredient : ingredients) {
            if (!validIngredients.values().stream().anyMatch(list -> list.contains(ingredient))) {
                return false;
            }
        }
        return true;
    }
}