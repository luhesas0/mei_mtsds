package com.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para atualização do status de uma tarefa.
 */
@Getter
@Setter
public class AtualizarStatusTarefaDTO {
    private Long tarefaId; //Identificador único da tarefa a ser atualizada
    private String novoStatus; //Novo status da tarefa (POR_REALIZAR, EM_CURSO, CONCLUIDA)

    /**
     * @return Detalhes do status a ser atualizado.
     */
    @Override
    public String toString(){
        return "AtualizarStatusTarefaDTO" +
                "tarefaId=" + tarefaId +
                ", novoStatus='" + novoStatus + '\'' +
                '}';
    }
}
