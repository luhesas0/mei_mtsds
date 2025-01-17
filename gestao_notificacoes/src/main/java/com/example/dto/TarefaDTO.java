package com.example.dto;

import com.example.enums.StatusTarefa;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de tarefas.
 * Usado para enviar ou receber dados de tarefas nos endpoints.
 */
@Getter
@Setter
public class TarefaDTO {
    private Long id; //Identificador único da tarefa
    private String descricao; //Descrição da tarefa
    private StatusTarefa status;// Status da tarefa (POR_REALIZAR, EM_CURSO, CONCLUIDA)
    private Long funcionarioId; //ID do funcionario responsável pela tarefa
    private Long veiculoId; //ID do veículo associado à tarefa (se houver)
    private LocalDateTime createdDate; //Data de criação da tarefa
    private LocalDateTime lastModifiedDate; //Data de última modificação da tarefa

    /**
     * @return uma string com os detalhes da tarefa para exibição.
     */
    @Override
    public String toString(){
        return "TarefaDTO{" +
                "id=" + id +
                ",descricao='" + descricao + '\'' +
                ", status='" + status + '\'' +
                ", funcionarioId=" + veiculoId +
                ", veiculoId=" + veiculoId +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

