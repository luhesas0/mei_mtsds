package com.example.data;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock getStockById(Long id);
}