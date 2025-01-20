package com.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exceção para indicar falta de permissões para uma operação.
 */
@Getter
@AllArgsConstructor
public class PermissaoNaoEncontrada extends Exception {
    private final String permissao;

    @Override
    public String getMessage(){
        return String.format("A permissão '%s' não foi encontrada.", permissao);
    }
}
