package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private double price;
    private Integer nivelMinimo;
}