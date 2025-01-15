package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO para criar um novo utilizador.
 * Inclui informações essenciais para a criação de um utilizador.
 */
@Getter
@Setter
public class CriarUtilizadorDTO {
    private Long id; //Opcional, utilizado apenas em atualizações

    @NotNull(message = "Nome é obrigatório")
    private String nome; //Nome completo do utilizador

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email; //Email único do utilizador

    @NotNull(message = "Password é obrigatória")
    private String password; //Password do utilizador

    private String telemovel; //Número de telemóvel do utilizador (opcional)

    private Boolean ativo = true; //Por padrão, o utilizador é criado como ativo

    private List<PapelDTO> roles; //Lista de papeis associados ao utilizador
}
