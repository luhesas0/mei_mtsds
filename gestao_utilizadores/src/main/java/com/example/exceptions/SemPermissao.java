package com.example.exceptions;

public class SemPermissao extends Exception {
    @Override
    public String getMessage(){
        return "Não possui permissões suficientes para realizar esta operação.";
    }
}
