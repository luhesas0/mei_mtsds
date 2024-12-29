package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriarUtilizadorDto {

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "Password é obrigatória")
    private String password;

    private List<String> roles; //Lista de roles associados
}
