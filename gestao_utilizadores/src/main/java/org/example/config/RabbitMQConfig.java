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
    public static final String EXCHANGE = "noticacao_exchange_requests";
    public static final String ROUTING_KEY = "notification_key_1";
    public static final String QUEUE = "notification_queue_request";

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
    public AmqpTemplate template(ConnectionFactory connectionFactory){
         RabbitTemplate template = new RabbitTemplate(connectionFactory);
         template.setMessageConverter(messageConverter());
         return template;
     }
}
