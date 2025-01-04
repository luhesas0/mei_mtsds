package com.estg.exceptions;

public class UtilizadorServiceUnexpectedException extends Exception {

    private String receivedMessage;

    @Override
    public String getMessage() {
        return String.format("(LS) OTService-UtilizadorService-Unexpected): " + "UtilizadorService replied %s", receivedMessage);
    }
}
