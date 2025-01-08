package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

/**
 * DTO para capturar credenciais de login enviadas pelo cliente.
 */

@Getter
@Setter
@Data
public class LoginRequest {
    @NotNull(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email; //Email do utilizador para login

    @NotNull(message = "Password é obrigatória")
    private String password; //Password do utilizador para autenticação
}
