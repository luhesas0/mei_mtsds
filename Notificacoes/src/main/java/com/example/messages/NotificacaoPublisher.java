package com.example.messages;

import com.example.dto.CriarNotificacaoDTO;
import com.example.dto.AtualizarStatusEntregaDTO;
import com.example.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Publicador de mensagens para RabbitMQ.
 */
@Component
@RequiredArgsConstructor
public class NotificacaoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NotificacaoPublisher.class);


    /**
     * Publica uma mensagem de notificação na fila de notificações.
     *
     * @param notificacao Dados da notificacao.
     */
    public void enviarNotificacao(CriarNotificacaoDTO notificacao){
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NOTIFICACOES,
                RabbitMQConfig.ROUTING_KEY_NOTIFICACOES,
                notificacao);
        logger.info("Notificação enviada: {}", notificacao);
    }

    /**
     * Publica uma mensagem de status de entrega na fila de notificações.
     *
     * @param statusEntrega Dados do status da entrega.
     */
    public void enviarStatusEntrega(AtualizarStatusEntregaDTO statusEntrega){
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NOTIFICACOES,
                RabbitMQConfig.ROUTING_KEY_NOTIFICACOES,
                statusEntrega);
        logger.info("Status de entrega enviado: {}", statusEntrega);
    }
}
