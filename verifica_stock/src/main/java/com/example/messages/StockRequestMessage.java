package com.example.messages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRequestMessage {

    // Getters and Setters
    private Long stockId;
    private int requestedQuantity;

}