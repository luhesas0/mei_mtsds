package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 *  DTO para transferência de dados de Registo de Login.
 */
@Getter
@Setter
public class RegistoLoginDTO {
    private String id; //Identificador único do registo de login
    private String utilizadorId; //Id do utilizador associado
    private String ipAddress; // Endereço IP de onde o login foi realizado
    private LocalDateTime dataLogin; // Data e hora do login
}
