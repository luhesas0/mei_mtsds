package com.estg.data;

import com.estg.enums.OrderStatus;
import com.estg.models.OrdemTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdemTrabalhoRepository extends JpaRepository<OrdemTrabalho, Integer> {

    List<OrdemTrabalho> findByStatus(OrderStatus status);

    List<OrdemTrabalho> findByFuncionarioId(Integer funcionarioId);

    @Query("FROM OrdemTrabalho ot WHERE ot.enderecoEntrega = :address AND ot.status = com.estg.enums.OrderStatus.PENDING")
    List<OrdemTrabalho> findPendingOrdersByAddress(@Param("address") String address);
}

