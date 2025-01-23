package com.example.controllers;

import com.example.dto.StockDTO;
import com.example.services.StockService;
import com.example.services.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private final StockService stockService;

    //private final ModelMapper modelMapper;

    @Autowired
    private final IngredientService ingredientService;

    public StockController(StockService stockService, IngredientService ingredientService) {
        this.stockService = stockService;
        this.ingredientService = ingredientService;
    }

    // Endpoint to get all stock items
    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStock() {
        List<StockDTO> stockList = stockService.getAllStock();
        return ResponseEntity.ok(stockList);
    }

    // Endpoint to get a stock item by ID
    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {
        StockDTO stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    // Endpoint to create a new stock item
    @PostMapping
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO) {
        StockDTO createdStock = stockService.createStock(stockDTO);
        return ResponseEntity.ok(createdStock);
    }

    // Endpoint to update a stock item
    @PutMapping("/{id}")
    public ResponseEntity<StockDTO> updateStock(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        StockDTO updatedStock = stockService.updateStock(id, stockDTO);
        return ResponseEntity.ok(updatedStock);
    }

    // Endpoint to delete a stock item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to get all ingredients
    @GetMapping("/ingredientes")
    public ResponseEntity<Map<String, List<String>>> getAllIngredients() {
        Map<String, List<String>> ingredients = ingredientService.getIngredientes();
        return ResponseEntity.ok(ingredients);
    }
}