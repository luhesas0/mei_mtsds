package com.estg.exceptions;

public class OrdemTrabalhoNotFound extends RuntimeException {
    public OrdemTrabalhoNotFound(Integer id) {
        super("Ordem de Trabalho com o id " + id + " não encontrada.");
    }
}
