package com.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exceção para indicar que o Utilizador já Existe.
 */
@Getter
@AllArgsConstructor
public class UtilizadorExistente extends Exception {
    private final String email;

    @Override
    public String getMessage(){
        return String.format("O utilizador com email '%' já existe no sistema.", email);
    }
}
