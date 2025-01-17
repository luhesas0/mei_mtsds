package com.example.enums;

/**
 * Enumeração para representar subtipos de funcionários no sistema.
 */
public enum TipoAtor {
    FUNCIONARIO("Funcionario"),
    FUNCIONARIO_ENTREGADOR("Funcionario Entregador"),
    FUNCIONARIO_STOCK("Funcionario de Stock"),
    FUNCIONARIO_MENU("Funcionário de Menu"),
    FUNCIONARIO_REPOSITORIO("Funcionário de Repositório");

    private final String descricao; //Descrição amigável para o tipo de ator do sistema

    TipoAtor(String descricao){
        this.descricao = descricao;
    }

    /**
     * Obtém a descrição do tipo de ator.
     *
     * @return Descrição do tipo de ator.
     */
    public String getDescricao(){
        return descricao;
    }
}
