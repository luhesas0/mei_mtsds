package org.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para papéis de utilizadores.
 * Inclui informações básicas de um papel atribuído a utilizadores.
 */
@Getter
@Setter
public class RoleDTO {
    private Integer id; //Identificador único do papel.
    private String nome;  //Nome do papel (ADMIN, FUNCIONARIO, CLIENTE)
    private String descricao; //Descrição opcional do papel
}

