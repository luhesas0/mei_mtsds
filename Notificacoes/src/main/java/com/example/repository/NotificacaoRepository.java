package com.example.repository;

import com.example.models.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface para gerir as
 * notificações enviadas a utilizadores (clientes ou funcionários).
 */


public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    /**
     * Busca todas as notificações de um utilizador específico.
     *
     * @param utilizadorId ID do utilizador (cliente ou funcionário).
     * @return Lista de notificações.
     */
    List<Notificacao> findByUtilizadorId(Long utilizadorId);

    /**
     * Busca todas as notificacoes associadas a uma entrega específica.
     *
     * @param entregaId ID da entrega.
     * @return Lista de notificações.
     */
    List<Notificacao> findByEntregaId(Long entregaId);
}
