package com.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para autenticação e troca de tokens JWT.
 */
@Getter
@Setter
public class AutenticacaoDTO {
    private String email;     // Email para login
    private String password; // Password para login
    private String token; //Token JWT gerado
}
