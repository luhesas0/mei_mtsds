package com.example.dto;

import com.example.enums.TipoUtilizador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Utilizador.
 * Não expõe dados sensíveis como password.
 */
@Getter
@Setter
public class UtilizadorDTO {
    private String id; //Identificador único do utilizador
    private String nome; //Nome completo do utilizador
    private String email; //Email único do utilizador
    private String telemovel; //Número de telemóvel do utilizador
    private TipoUtilizador tipoUtilizador; //Tipo do utilizador (ADMIN, CLIENTE, FUNCIONARIO)
    private Boolean ativo; //Status do utilizador
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
}
