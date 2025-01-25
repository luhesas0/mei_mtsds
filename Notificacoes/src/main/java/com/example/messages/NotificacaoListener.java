package com.example.messages;

import com.example.config.RabbitMQConfig;
import com.example.dto.CriarNotificacaoDTO;
import com.example.dto.AtualizarStatusEntregaDTO;
import com.example.service.NotificacaoService;
import com.example.service.StatusEntregaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *  Listener para consumir mensagens de RabbitMQ.
 */
@Component
@RequiredArgsConstructor
public class NotificacaoListener {

    private final NotificacaoService notificacaoService;
    private final StatusEntregaService statusEntregaService;
    private static final Logger logger = LoggerFactory.getLogger(NotificacaoListener.class);

    /**
     * Processa mensagens de notificações recebidas na fila.
     *
     * @param notificacao Dados da notificação.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICACOES)
    public void processarNotificacao(CriarNotificacaoDTO notificacao) {
        try {
            logger.info("Mensagem de notificação recebida: {}", notificacao);
            notificacaoService.registarNotificacao(notificacao);
        } catch (Exception e) {
            logger.error("Erro ao processar notificação: {}", notificacao, e);
        }
    }

    /**
     * Processa mensagens de avaliações recebidas na fila.
     *
     * @param notificacaoDTO Dados da avaliação.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_AVALIACOES)
    public void processarAvaliacao(CriarNotificacaoDTO notificacaoDTO){
        try{
            logger.info("Mensagem de avaliação recebida: {}", notificacaoDTO);
            notificacaoService.registarNotificacao(notificacaoDTO);
        } catch (Exception e){
            logger.error("Erro ao processar avaliação: {}", notificacaoDTO, e);
        }
    }

    /**
     * Processa mensagens de status de entrega recebidas na fila.
     *
     * @param statusEntrega Dados do status da entrega.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_STATUS)
    public void processarStatusEntrega(AtualizarStatusEntregaDTO statusEntrega) {
        try {
            logger.info("Mensagem de status de entrega recebida: {}", statusEntrega);
            statusEntregaService.atualizarStatusEntrega(statusEntrega);
        } catch (Exception e) {
            logger.error("Erro ao processar status de entrega: {}", statusEntrega, e);
        }
    }
}