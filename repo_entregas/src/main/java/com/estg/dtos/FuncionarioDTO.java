package com.estg.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private String email;
    private List<String> roles;
}
