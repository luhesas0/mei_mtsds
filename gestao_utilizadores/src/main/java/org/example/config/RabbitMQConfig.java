package org.example.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //Constantes de fila, exchange e routing key
    public static final String QUEUE = "nova_avaliacao_confirmation_requests";
    public static final String EXCHANGE = "exchange_confirmation_requests";
    public static final String ROUTING_KEY = "routing_Key_1";

     @Bean
    public Queue queue(){
         return new Queue(QUEUE);
     }

     @Bean
    public TopicExchange exchange(){
         return new TopicExchange(EXCHANGE);
     }

     @Bean
     public Binding binding(Queue queue, TopicExchange exchange){
         return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
     }

     @Bean
    public MessageConverter messageConverter(){
         return new Jackson2JsonMessageConverter();
     }

     @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
         RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
         rabbitTemplate.setMessageConverter(messageConverter());
         return rabbitTemplate;
     }
}
