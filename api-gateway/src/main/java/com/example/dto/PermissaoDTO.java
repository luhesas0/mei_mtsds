package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List; //Import necessário para a classe List

@Getter
@Setter

public class PermissaoDTO {
    private String utilizadorId; //Id do utilizador
    private List<String> permissoes; //Lista de permissoes atribuidas
}
