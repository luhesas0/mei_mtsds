package com.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exceção para indicar que o utilizador não foi encontrado.
 */
@Getter
@AllArgsConstructor
public class UtilizadorNaoExiste extends Exception {
    private final String email;

    @Override
    public String getMessage(){
        return String.format("O utilizador com email '%s' não foi encontrado no sistema.", email);
    }
}
