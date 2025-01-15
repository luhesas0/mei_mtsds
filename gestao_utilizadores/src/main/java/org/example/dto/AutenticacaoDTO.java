package org.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para representar solicitações de autenticação.
 */
@Getter
@Setter
public class AuthRequestDTO {
    private String email;     // Email para login
    private String password; // Password para login
}
