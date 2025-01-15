package org.example.exceptions;

/**
 * Exceção para indicar falta de permissões para uma operação.
 */
public class SemPermissao extends RuntimeException {

    @Override
    public String getMessage(){
        return "O utilizador não possui permissões suficientes para realizar esta ação.";
    }
}
