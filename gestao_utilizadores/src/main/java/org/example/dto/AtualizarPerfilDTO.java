package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarPerfilDto {
    private String nome;

    @Email(message = "Formato de email inválido")
    private String email;

    private String password;
}
