package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UtilizadorDto {
    private Long id;

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    private Boolean status;
    private List<String> roles; //Lista de roles associados
}
