package com.example.models;

/**
 * Enumeração que define os papéis dos utilizadores no sistema.
 * Cada tipo de utilizador pode ter permissões específicas associadas.
 */
public enum TipoUtilizador {
    ADMIN("Administrador do sistema e todos os acessos"),
    CLIENTE("Cliente que utiliza os serviços"),
    FUNCIONARIO("Funcionario geral"),
    FUNCIONARIO_MENU("Funcionário responsável por gerir menus"),
    FUNCIONARIO_ENTREGA("Funcionário responsável por entregas"),
    FUNCIONARIO_STOCK("Funcionário responsável pelo stock");

    private final String role;

    TipoUtilizador(String role){
        this.role = role;
    }

    /**
     * Obtém o papel associado ao enum.
     *
     * @return String representando o papel.
     */
    public String getRole(){
        return role;
    }
}
