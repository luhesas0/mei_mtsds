package org.example.messaging.producer;

import org.example.config.RabbitMQConfig;
import org.example.dto.NotificacaoDto;
import org.example.models.Notificacoes;
import org.example.models.Utilizador;
import org.example.repository.NotificacoesRepository;
import org.example.repository.UtilizadorRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    private final NotificacoesRepository notificacoesRepository;
    private final UtilizadorRepository utilizadorRepository;

    public NotificationProducer(RabbitTemplate rabbitTemplate,
                                NotificacoesRepository notificacoesRepository,
                                UtilizadorRepository utilizadorRepository){
        this.rabbitTemplate = rabbitTemplate;
        this.notificacoesRepository = notificacoesRepository;
        this.utilizadorRepository = utilizadorRepository;
    }

    public void sendNotification(Long userId, NotificacaoDto notificacaoDto){
        // Buscar o utilizador na base de dados
        Utilizador utilizador = utilizadorRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        // Criar a entidade de notificação
        Notificacoes notificacao = new Notificacoes();
        notificacao.setUtilizador(utilizador);
        notificacao.setMensagem(notificacao.getMensagem());
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacoesRepository.save(notificacao);

        // Enviar para a fila RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, notificacaoDto);
        System.out.println("Mensagem enviada para a fila:" + notificacaoDto);
    }
}
