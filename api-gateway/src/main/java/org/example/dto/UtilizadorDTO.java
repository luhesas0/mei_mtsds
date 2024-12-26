package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String id; //ID do Utilizador
    private String nome; //Nome do Utilizador
    private List<String>roles; //Lista de funções (role) do utilizador
}
