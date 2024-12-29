package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotNull(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "Password é obrigatória")
    private String password;
}
