package org.example.enums;

/**
 * Enumeração que define os papéis dos utilizadores no sistema.
 */
public enum UtilizadorRule {
    ADMIN("Administrador"),
    FUNCIONARIO("Funcionario"),
    CLIENTE("Cliente");

    private final String role;

    UtilizadorRule(String role){
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
