package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List; //Import necessário para a classe List

@Getter
@Setter
public class PermissionsDTO {
    private String userId; //Id do utilizador
    private List<String> permissions; //Lista de permissões atribuidas
}
