package com.example.messaging;

import com.example.config.RabbitMQConfig;
import com.example.models.OrdemTrabalho;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPublisher {
    private final RabbitTemplate rabbitTemplate;

    public OTPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(OrdemTrabalho ordemTrabalho) {
        String exchangeName = RabbitMQConfig.EXCHANGE;
        String routingKey = RabbitMQConfig.ROUTING_KEY;
        rabbitTemplate.convertAndSend(exchangeName, routingKey, ordemTrabalho);
        System.out.println("Ordem de trabalho publicada: " + ordemTrabalho);
    }
}
