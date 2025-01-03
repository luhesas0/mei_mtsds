package com.estg.exceptions;

public class UtilizadoresUnexistingFuncionarioException extends Exception {

    private String receivedMessage;

    @Override
    public String getMessage() {
        return String.format("(LS) OTService-UtilizadorService-Unexisting Funcionario): " + "UtilizadorService replied %s", receivedMessage);
    }
}
