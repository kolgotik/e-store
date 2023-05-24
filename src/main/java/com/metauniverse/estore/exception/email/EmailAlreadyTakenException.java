package com.metauniverse.estore.exception.email;

public final class EmailAlreadyTakenException extends RuntimeException {
    private final static String EMAIL_ALREADY_TAKEN_MSG = "Email already taken";

    public EmailAlreadyTakenException() {
        super(EMAIL_ALREADY_TAKEN_MSG);
    }
}
