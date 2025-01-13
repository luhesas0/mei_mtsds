package org.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para a resposta de autenticação contendo o token JWT e o tipo.
 */

@Getter
@Setter
public class LoginResponseDTO {
    private String token;  //Token JWT gerado
    private String tipoToken= "Bearer"; //Tipo padrão de token
}
