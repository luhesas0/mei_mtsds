package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockClient {

    private final RestTemplate restTemplate;
    private final String verificaStockUrl;

    public StockClient(RestTemplate restTemplate, @Value("${verifica_stock.url}") String verificaStockUrl) {
        this.restTemplate = restTemplate;
        this.verificaStockUrl = verificaStockUrl;
    }

    public boolean checkStock() {
        try {
            Boolean response = restTemplate.getForObject(verificaStockUrl + "/check", Boolean.class);
            return response != null && response;
        } catch (Exception e) {
            System.err.println("Erro ao verificar stock: " + e.getMessage());
            return false;
        }
    }
}
