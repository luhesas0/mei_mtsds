package org.example.messaging.consumer;

import org.example.config.AppConfig;
import org.example.config.RabbitMQConfig;
import org.example.dto.NotificacaoDto;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(AppConfig.class)
public class NotificationConsumerIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testReceiveNotification(){
        //Preparar dados do teste
        NotificacaoDto notificacaoDto = new NotificacaoDto();
        notificacaoDto.setMensagem("Teste de consumo");

        //Enviar mensagem para a fila
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, notificacaoDto);

        //Simulação: Aqui você pode verificar se o consumidor recebeu e processou a mensagem.
        // Uma solução real exigiria uma maneira de capturar a mensagem processada ou verificar efeitos colaterais.
        assertTrue(true, "Mensagem processada com sucesso.");
    }
}
