package com.metauniverse.estore.exception.email;

public final class InvalidEmailException extends RuntimeException {
    private static final String EMAIL_NOT_VALID_MSG = "Email is not valid";

    public InvalidEmailException() {
        super(EMAIL_NOT_VALID_MSG);
    }
}
