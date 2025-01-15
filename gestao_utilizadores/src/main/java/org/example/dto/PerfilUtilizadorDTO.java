package org.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para transferência de dados de registos de permissões.
 */
@Getter
@Setter
public class RegistoPermissaoDTO {
    private String id;
    private String idUtilizador;

}
