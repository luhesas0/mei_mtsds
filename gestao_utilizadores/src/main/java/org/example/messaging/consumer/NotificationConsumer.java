package org.example.messaging.consumer;

import org.example.config.RabbitMQConfig;
import org.example.dto.NotificacaoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveNotification(NotificacaoDto notificacaoDto){
        System.out.println("Mensagem recebida da fila:" + notificacaoDto);

        // Aqui, é para adicionar lógica para processar a mensagem conforme necessário
        // Enviar email ou atualizar algum estado no sistema
        // Necessário consumir a avaliação da entrega pelo cliente?
        // Guardar no perfil do cliente, administrador e funcionário.
    }
}
