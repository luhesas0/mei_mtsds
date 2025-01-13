package org.example.repository;

import org.example.models.AutorizacaoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerir autorizações de tarefas
 * Métodos para CRUD e consultas específicas.
 */
public interface AutorizacaoTarefaRepository extends JpaRepository<AutorizacaoTarefa, Long> {

    /**
     * Busca autorizações por utilizador.
     *
     * @param utilizadorId ID do utilizador.
     * @return Lista de autorizações associadas ao utilizador.
     */
    List<AutorizacaoTarefa> findByUtilizadorId(Long utilizadorId);

    /**
     * Verifica se uma autorização específica existe.
     *
     * @param utilizadorId ID do utilizador.
     * @param tarefaId     ID da tarefa.
     * @return True se a autorização existir, caso contrário False.
     */
    boolean existsByUtilizadorIdAndTarefaId(Long utilizadorId, Long tarefaId);

    /**
     * Busca uma autorização específica por utilizador e tarefa.
     *
     * @param utilizadorId ID do utilizador.
     * @param tarefaId     ID da tarefa.
     * @return Autorização encontrada ou vazia.
     */
    Optional<AutorizacaoTarefa> findByUtilizadorIdAndTarefaId(Long utilizadorId,Long tarefaId );
}
