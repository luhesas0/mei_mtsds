package com.example.messages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockResponseMessage {

    // Getters and Setters
    private Long stockId;
    private String name;
    private int availableQuantity;
    private double price;

}