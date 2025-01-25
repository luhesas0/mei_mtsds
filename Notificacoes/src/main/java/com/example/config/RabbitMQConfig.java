package com.example.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurações para integração com RabbitMQ,
 * incluindo filas, exchanges e bindings.
 */
@Configuration
public class RabbitMQConfig {

    // Nomes das filas
    public static final String QUEUE_NOTIFICACOES = "notificacoes.queue";
    public static final String QUEUE_AVALIACOES = "avaliacoes.queue";
    public static final String QUEUE_STATUS = "status.queue";

    // Nomes dos exchanges
    public static final String EXCHANGE_NOTIFICACOES = "notificacoes.exchange";
    public static final String EXCHANGE_AVALIACOES = "avaliacoes.exchange";

    // Rountig Keys
    public static final String ROUTING_KEY_NOTIFICACOES = "notificacoes.routingkey";
    public static final String ROUTING_KEY_AVALIACOES = "avaliacoes.routingkey";
    public static final String ROUTING_KEY_STATUS = "status.routingkey";

    /**
     *  Declaração da fila para notificações.
     */
    @Bean
    public Queue notificacoesQueue(){
        return new Queue(QUEUE_NOTIFICACOES, true); //Fila persistente
    }

    /**
     * Declaração da fila para avaliações.
     */
    @Bean
    public Queue avaliacoesQueue(){
        return new Queue(QUEUE_AVALIACOES, true); //Fila persistente
    }

    /**
     * Declaração da fila para status de entrega.
     */
    @Bean
    public Queue statusQueue(){
        return new Queue(QUEUE_STATUS, true); //Fila persistente
    }

    /**
     * Declaração do exchange para notificações.
     */
    @Bean
    public TopicExchange notificacoesExchange(){
        return new TopicExchange(EXCHANGE_NOTIFICACOES);
    }

    /**
     *  Declaração do exchange para avaliações.
     */
    @Bean
    public TopicExchange avaliacoesExchange(){
        return new TopicExchange(EXCHANGE_AVALIACOES);
    }

    /**
     *  Bind entre a fila de notificações e o exchange de notificações.
     */
    @Bean
    public Binding notificacoesBinding(){
        return BindingBuilder.bind(notificacoesQueue())
                .to(notificacoesExchange())
                .with(ROUTING_KEY_NOTIFICACOES);
    }

    /**
     * Bind entre a fila de avaliações e o exchange de avaliações.
     */
    @Bean
    public Binding avaliacoesBinding(){
        return BindingBuilder.bind(avaliacoesQueue())
                .to(avaliacoesExchange())
                .with(ROUTING_KEY_AVALIACOES);
    }

    /**
     * Bind entre a fila de status e o exchange de notificações.
     */
    @Bean
    public Binding statusBinding(){
        return BindingBuilder.bind(statusQueue())
                .to(notificacoesExchange())
                .with(ROUTING_KEY_STATUS);
    }
}
