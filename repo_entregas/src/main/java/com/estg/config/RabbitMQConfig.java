package com.estg.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitMQConfig {
    // OT = Ordem de trabalho

        public static final String QUEUE = "OT_queue";
        public static final String EXCHANGE = "OT_exchange";
        public static final String ROUTING_KEY = "OT_routingKey";

        @Bean
        public Queue queue() {
            return new Queue(QUEUE);
        }

        @Bean
        public TopicExchange exchange() {
            return new TopicExchange(EXCHANGE);
        }

        @Bean
        public Binding binding(Queue queue, TopicExchange exchange) {
            return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
        }

}
