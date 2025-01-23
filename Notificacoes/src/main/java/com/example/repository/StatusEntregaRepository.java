package com.example.repository;

import com.example.models.StatusEntrega;
import com.example.enums.StatusEntregaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface para gerir os dados relacionados ao status das entregas.
 */

public interface StatusEntregaRepository extends JpaRepository<StatusEntrega, Long> {

    /**
     * Busca os status associados a uma entrega específica.
     *
     * @param entregaId ID da entrega.
     * @return Lista de status.
     */
    List<StatusEntrega> findByEntregaId(Long entregaId);

    /**
     * Busca os status associados a um utilizador específico.
     *
     * @param utilizadorId ID do funcionário responsável.
     * @return Lista de status.
     */
    List<StatusEntrega> findByUtilizadorId(Long utilizadorId);

    /**
     * Busca os status pelo status da entrega.
     *
     * @param status Status da entrega.
     * @return Lista de status que correspondem ao critério.
     */
    List<StatusEntrega> findByStatus(StatusEntregaEnum status);
}
