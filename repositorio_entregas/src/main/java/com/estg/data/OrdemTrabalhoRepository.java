package com.estg.data;

import com.estg.enums.OrderStatus;
import com.estg.models.OrdemTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemTrabalhoRepository extends JpaRepository<OrdemTrabalho, Integer> {
    //find by status
    List<OrdemTrabalho> findByStatus(OrderStatus status);
    //find by funcionario_id
    List<OrdemTrabalho> findByFuncionarioId(Integer funcionarioId);
}

