package org.example.enums;

/**
 * Enumeração para representar o status das tarefas.
 */
public enum StatusTarefa {
    POR_REALIZAR("Por Realizar"),
    EM_CURSO("Em Curso"),
    CONCLUIDA("Concluída");

    private final String descricao;//Campo para armazenar a descrição do status

    //Construtor do enum
    StatusTarefa(String descricao){
        this.descricao = descricao;
    }

    //Método para obter a descrição do status
    public String getDescricao(){
        return descricao;
    }
}
