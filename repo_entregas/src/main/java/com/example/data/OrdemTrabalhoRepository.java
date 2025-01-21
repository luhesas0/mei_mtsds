package com.example.data;

import com.example.enums.OrderStatus;
import com.example.models.OrdemTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdemTrabalhoRepository extends JpaRepository<OrdemTrabalho, Integer> {

    List<OrdemTrabalho> findByStatus(OrderStatus status);

    List<OrdemTrabalho> findByFuncionarioId(Integer funcionarioId);

    @Query("FROM OrdemTrabalho ot WHERE ot.enderecoEntrega = :address AND ot.status = com.example.enums.OrderStatus.PENDING")
    List<OrdemTrabalho> findPendingOrdersByAddress(@Param("address") String address);
}

