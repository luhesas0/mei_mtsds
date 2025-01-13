package org.example.repository;

import org.example.models.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para gerir operações relacionadas à entidade Tarefa.
 */
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    /**
     * Encontra todas as tarefas atribuidas a um funcionário específico.
     *
     * @param funcionarioId ID do funcionário.
     * @return Lista de tarefas atribuídas ao funcionário.
     */
    List<Tarefa> findByFuncionarioId(Long funcionarioId);

    /**
     * Busca tarefas por status.
     *
     * @param status Status da tarefa.
     * @return Lista de tarefas com status especificado.
     */
    List<Tarefa> findByStatus(String status);

    /**
     * Verifica se uma tarefa existe pelo ID.
     *
     * @param tarefaId ID da tarefa.
     * @return True se a tarefa existir, caso contrário False.
     */
    boolean existsById(Long tarefaId);
}
