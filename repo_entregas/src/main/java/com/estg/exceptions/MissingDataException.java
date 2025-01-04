package com.estg.exceptions;

public class MissingDataException extends Exception {

    private String missingData;

    @Override
    public String getMessage() {
        return String.format("(LS) OTService-MissingData: " + "Missing data in utilizador record: %s", missingData);
    }
}
