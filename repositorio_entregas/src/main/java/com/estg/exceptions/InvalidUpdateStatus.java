package com.estg.exceptions;

public class InvalidUpdateStatus extends RuntimeException {
    public InvalidUpdateStatus(Integer id) {
        super("Ordem com o id " + id + " é inválida. Não é possível atualizar o status.");
    }
}
