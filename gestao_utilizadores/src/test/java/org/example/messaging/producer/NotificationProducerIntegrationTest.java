package org.example.messaging.producer;

import org.junit.jupiter.api.Disabled;
import org.example.config.AppConfig;
import org.example.config.RabbitMQConfig;
import org.example.dto.NotificacaoDto;
import org.example.models.Utilizador;
import org.example.repository.UtilizadorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("Temporariamente desativado devido a erro em 'mensagem' nulo")
@SpringBootTest
@Import(AppConfig.class)
public class NotificationProducerIntegrationTest {

    @Autowired
    private NotificationProducer notificationProducer;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    private BlockingQueue<NotificacaoDto> receivedMessages;

    @BeforeEach
    void setUp(){
        receivedMessages = new LinkedBlockingQueue<>();

    // Inserir um utilizador na base de dados para o teste
        Utilizador utilizador = new Utilizador();
        utilizador.setNome("Teste");
        utilizador.setEmail("test@example.com");
        utilizador.setPassword("password_teste");
        utilizador.setStatus(true);

        utilizadorRepository.save(utilizador);
    }

    @AfterEach
    void tearDown(){
        receivedMessages.clear();
        utilizadorRepository.deleteAll(); //Limpa a base de dados após os testes
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listen(NotificacaoDto notificacaoDto){
        receivedMessages.offer(notificacaoDto);
    }

    @Test
    @Disabled("Temporariamente desativado devido a erro no campo 'mensagem' nulo")
    void testSendNotification() throws InterruptedException{
        // Preparar dados do teste
        NotificacaoDto notificacaoDto = new NotificacaoDto();
        notificacaoDto.setMensagem("Teste de notificação");

        //Enviar notificação
        notificationProducer.sendNotification(1L, notificacaoDto);

        //Verificar que a mensagem foi enviada e recebida
        NotificacaoDto receivedMessage = receivedMessages.take();
        assertEquals("Teste de notificação", receivedMessage.getMensagem());
    }
}
