package com.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // Definindo as filas
    @Bean
    public Queue stockRequestQueue() {
        return new Queue("stockRequestQueue", true);
    }

    @Bean
    public Queue stockResponseQueue() {
        return new Queue("stockResponseQueue", true);
    }

    @Bean
public Queue stockAlertQueue() {
    return new Queue("stock.alert.queue");
}

}