package com.example.exceptions;

public class InvalidStockUpdateException extends RuntimeException {

    public InvalidStockUpdateException(String message) {
        super(message);
    }

    public InvalidStockUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}