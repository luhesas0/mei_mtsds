package com.example.exceptions;

public class InvalidOrdemTrabalho extends RuntimeException {
    public InvalidOrdemTrabalho(Integer id) {
        super("Ordem de Trabalho com o id " + id + " é inválida.");
    }
}
