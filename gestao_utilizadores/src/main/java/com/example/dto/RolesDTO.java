package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * DTO para transferência de dados de Papéis (Roles).
 */
@Getter
@Setter
public class PapelDTO {
    private String id; //Identificador único do papel.
    private String nome;  //Nome do papel (ADMIN, FUNCIONARIO, CLIENTE)
    private String descricao; //Descrição opcional do papel
    private List<PermissaoDTO> permissoes; // Permissoes associadas ao papel
}

