package com.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para transferência de dados de Permissões.
 */
@Getter
@Setter
public class PermissaoDTO {
    private Integer id; // Identificador único da permissão
    private String nome; // Nome da permissão
    private String descricao; // Descrição detalhada da permissão
}
