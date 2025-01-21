// File: src/main/java/org/estg/services/IngredientService.java
package com.example.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IngredientService {

    public Map<String, List<String>> getIngredientes() {
        // Implementação para obter os ingredientes
        return Map.of("ingredientes", List.of("Ingrediente1", "Ingrediente2"));
    }
}