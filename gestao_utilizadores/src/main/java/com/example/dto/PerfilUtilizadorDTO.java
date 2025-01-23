package com.example.dto;

import com.example.enums.TipoUtilizador;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para transferência de informações do perfil do utilizador
 */
@Getter
@Setter
public class PerfilUtilizadorDTO {
    private String id;
    private String nome;
    private TipoUtilizador tipoUtilizador; //Tipo do utilizador (Admin, Cliente, Funcionário)
    private String repositorio; // Unidade ou repositório associado (ex.: centro escolar, repositório de entregas)
    private Integer nivel; // Nível hierárquico do utilizador (1 - Admin, 2 - Funcionário, 3 - Cliente)
}
