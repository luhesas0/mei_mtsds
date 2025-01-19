package com.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurações para integração com RabbitMQ.
 * Define filas e fornece a configuração de conexão.
 */
@Configuration
public class RabbitMQConfig {

    /**
     * Define a fila para mensagens de e-mail.
     *
     * @return a configuração da fila.
     */
    @Bean
    public Queue emailQueue(){
        return new Queue("emailQueue", true); // Fila persistente
    }

    /**
     * Configura o RabbitTemplate para envio de mensagens.
     *
     * @param connectionFactory a fábrica de conexões com o RabbitMQ.
     * @return o RabbitTemplate configurado.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setDefaultReceiveQueue("emailQueue");
        return rabbitTemplate;
    }
}
