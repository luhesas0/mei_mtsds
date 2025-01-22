package com.example.messages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRequestMessage {
    private Long stockId;
    @Getter
    private int requestedQuantity;
    private String mensagem;

    public StockRequestMessage(Long stockId, String mensagem) {
        this.stockId = stockId;
        this.mensagem = mensagem;
    }
}