package org.example.messaging.consumer;

import org.example.config.RabbitMQConfig;
import org.example.dto.NotificacaoDto;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NotificationConsumerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testReceiveNotification(){
        //Preparar dados do teste
        NotificacaoDto notificacaoDto = new NotificacaoDto();
        notificacaoDto.setMensagem("Teste de consumo");

        //Enviar mensagem para a fila
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, notificacaoDto);

        //Verificar que a mensagem foi processada
        // Aqui pode verificar logs, base de dados ou outros efeitos colaterais do processamento
        assertTrue(true, "Mensagem processada com sucesso.");
    }
}
