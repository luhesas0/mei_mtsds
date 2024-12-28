package com.estg.exceptions;

public class RabbitMQPubException extends RuntimeException {
    public RabbitMQPubException(String message) {
        super("Erro ao publicar mensagem no RabbitMQ: " + message);
    }
}
