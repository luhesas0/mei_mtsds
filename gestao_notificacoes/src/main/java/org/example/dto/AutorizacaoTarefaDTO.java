package org.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para autorizações de tarefas, especificando permissões para diferentes utilizadores.
 */
@Getter
@Setter
public class AutorizacaoTarefaDTO {
    private Long utilizadorId; //Identificador do utilizador
    private String role; //Papel do utilizador (ADMIN, FUNCIONARIO_ENTREGADOR, CLIENTE)
    private String acao; //Ação permitida (CRIAR, LER, ATUALIZAR, REMOVER)
    private String recurso; //Recurso associado (TAREFA, MENU, PERFIL)

    /**
     * @return String representando os detalhes da autorização
     */
    @Override
    public String toString(){
        return "AutorizacaoTarefaDTO{" +
                "utilizadorId=" + utilizadorId +
                ", role='" + role + '\'' +
                ", acao='" + acao + '\'' +
                ", recurso='" + recurso + '\'' +
                '}';
    }
}
