package com.estg.exceptions;

public class CriacaoMenuUnexistingMenuException extends RuntimeException {
    private String receivedMessage;

    @Override
    public String getMessage() {
        return String.format("(LS) OTService-MenuService-Unexisting Menu): " + "MenuService replied %s", receivedMessage);
    }
}
