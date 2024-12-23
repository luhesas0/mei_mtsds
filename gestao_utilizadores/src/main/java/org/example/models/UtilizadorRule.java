package com.example.models;

public enum UtilizadorRule {
    ADMIN("admin"),
    FUNCIONARIO("funcionario"),
    CLIENTE("cliente");

    private final String role;

    UtilizadorRule(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
