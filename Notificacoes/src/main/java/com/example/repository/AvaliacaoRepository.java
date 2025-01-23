package com.example.repository;

import com.example.models.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface responsável por aceder os dados da entidade Avaliacao.
 */

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    /**
     * Busca todas as avaliações de um utilizador específico.
     *
     * @param utilizadorId ID do utilizador (cliente ou funcionário).
     * @return Lista de avaliações do utilizador.
     */
    List<Avaliacao> findByUtilizadorId(Long utilizadorId);

    /**
     * Busca todas as avaliações associadas a uma entrega específica.
     *
     * @param entregaId ID da entrega.
     * @return Lista de avaliações da entrega.
     */
    List<Avaliacao> findByEntregaId(Long entregaId);
}
