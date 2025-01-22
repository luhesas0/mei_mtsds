package com.example.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class RouteResponseDTO {
    @NotBlank(message = "O campo 'origem' não pode estar vazio.")
    @Pattern(regexp = "^[a-zA-Z0-9, ]+$", message = "A origem deve conter apenas letras, números, vírgulas e espaços.")
    private String origem;

    @NotBlank(message = "O campo 'destino' não pode estar vazio.")
    @Pattern(regexp = "^[a-zA-Z0-9, ]+$", message = "O destino deve conter apenas letras, números, vírgulas e espaços.")
    private String destino;

    private Object routes;
}
