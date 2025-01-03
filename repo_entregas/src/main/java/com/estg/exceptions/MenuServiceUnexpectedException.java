package com.estg.exceptions;

public class MenuServiceUnexpectedException extends Exception {
    private String receivedMessage;

    @Override
    public String getMessage() {
        return String.format("(LS) OTService-MenuService-Unexpected): " + "MenuService replied %s", receivedMessage);
    }
}
