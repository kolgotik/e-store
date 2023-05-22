package com.metauniverse.estore.exception;

public class EmailAlreadyTakenException extends RuntimeException {
    private final static String EMAIL_ALREADY_TAKEN_MSG = "Email: %s is already taken";

    public EmailAlreadyTakenException(String email) {
        super(String.format(EMAIL_ALREADY_TAKEN_MSG, email));
    }
}
