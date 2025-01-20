package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class PermissoesDTO {
    private Integer utilizadorId; //Id do utilizador
    private String nome;
    private List<String> permissoes; //Lista de permissoes atribuidas
}
