package com.estg.messaging;

import com.estg.config.RabbitMQConfig;
import com.estg.dtos.OrdemTrabalhoDTO;
import com.estg.models.OrdemTrabalho;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPublisher {

    private static final Logger logger = LoggerFactory.getLogger(OTPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public OTPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Publishes an OrdemTrabalho event to RabbitMQ.
     *
     * @param ordemTrabalho The OrdemTrabalho object to be published.
     */
    public void publish(OrdemTrabalho ordemTrabalho) {
        try {
            String message = objectMapper.writeValueAsString(ordemTrabalho);
            logger.info("Publishing OrdemTrabalho event: {}", message);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    message
            );

            logger.info("OrdemTrabalho event published successfully.");
        } catch (JsonProcessingException e) {
            logger.error("Error serializing OrdemTrabalho for publishing: {}", e.getMessage(), e);
        }
    }
}
