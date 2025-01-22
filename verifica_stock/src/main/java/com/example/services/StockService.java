package com.example.services;

import com.example.data.StockRepository;
import com.example.dto.StockDTO;
import com.example.exceptions.InvalidStockUpdateException;
import com.example.exceptions.OutOfStockException;
import com.example.model.Stock;
import com.example.messages.StockRequestMessage;
import com.example.messages.StockResponseMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IngredientValidator ingredientValidator;

    public StockResponseMessage getStock(Long stockId) {
        Stock stock = stockRepository.getStockById(stockId);
        if (stock == null) {
            throw new OutOfStockException("Stock not found for id: " + stockId);
        }
        StockResponseMessage response = new StockResponseMessage();
        response.setStockId(stock.getId());
        response.setName(stock.getName());
        response.setAvailableQuantity(stock.getQuantity());
        response.setPrice(stock.getPrice());
        return response;
    }

    public void updateStock(StockRequestMessage request) {
        Stock stock = stockRepository.getStockById(request.getStockId());
        if (stock == null) {
            throw new InvalidStockUpdateException("Stock not found for id: " + request.getStockId());
        }
        if (request.getRequestedQuantity() > stock.getQuantity()) {
            throw new OutOfStockException("Requested quantity exceeds available stock for id: " + request.getStockId());
        }
        stock.setQuantity(stock.getQuantity() - request.getRequestedQuantity());
    }

    public void addStock(Stock stock) {
        if (!ingredientValidator.validateIngredients(stock.getIngredients())) {
            throw new IllegalArgumentException("Invalid ingredients in stock");
        }
        stockRepository.save(stock);
    }

    public List<StockDTO> getAllStock() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(stock -> new StockDTO(stock.getId(), stock.getName(), stock.getQuantity(), stock.getPrice(), stock.getNivelMinimo()))
                .collect(Collectors.toList());
    }

    public StockDTO getStockById(Long id) {
        Stock stock = stockRepository.getStockById(id);
        if (stock == null) {
            throw new OutOfStockException("Stock not found for id: " + id);
        }
        return new StockDTO(stock.getId(), stock.getName(), stock.getQuantity(), stock.getPrice(), stock.getNivelMinimo());
    }

    public StockDTO createStock(StockDTO stockDTO) {
        Stock stock = new Stock();
        stock.setName(stockDTO.getName());
        stock.setQuantity(stockDTO.getQuantity());
        stock.setPrice(stockDTO.getPrice());
        stock.setNivelMinimo(stockDTO.getNivelMinimo());
        stockRepository.save(stock);
        return new StockDTO(stock.getId(), stock.getName(), stock.getQuantity(), stock.getPrice(), stock.getNivelMinimo());
    }

    public void deleteStock(Long id) {
        Stock stock = stockRepository.getStockById(id);
        if (stock == null) {
            throw new OutOfStockException("Stock not found for id: " + id);
        }
        stockRepository.delete(stock);
    }

    public StockDTO updateStock(Long id, StockDTO stockDTO) {
        Stock stock = stockRepository.getStockById(id);
        if (stock == null) {
            throw new InvalidStockUpdateException("Stock not found for id: " + id);
        }
        stock.setName(stockDTO.getName());
        stock.setQuantity(stockDTO.getQuantity());
        stock.setPrice(stockDTO.getPrice());
        stock.setNivelMinimo(stockDTO.getNivelMinimo());
        stockRepository.save(stock);
        verificarNivelMinimo(stock);
        return new StockDTO(stock.getId(), stock.getName(), stock.getQuantity(), stock.getPrice(), stock.getNivelMinimo());
    }

    private void verificarNivelMinimo(Stock stock) {
        if (stock.getQuantity() < stock.getNivelMinimo()) {
            enviarAlerta(stock);
        }
    }

    private void enviarAlerta(Stock stock) {
        StockRequestMessage mensagem = new StockRequestMessage(stock.getId(), "Stock abaixo do limite mínimo");
        rabbitTemplate.convertAndSend("stock.alert.queue", mensagem);
    }
}