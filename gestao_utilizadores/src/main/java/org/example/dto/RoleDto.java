package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
    private Long id;

    @NotNull(message = "Nome da função é obrigatório")
    private String nome;
}
