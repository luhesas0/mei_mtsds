package com.example.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockResponseMessage {
    private Long stockId;
    private String name;
    private Integer availableQuantity;
    private double price;
}