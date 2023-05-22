package com.metauniverse.estore.exception;

public class InvalidEmailException extends RuntimeException {
    private static final String EMAIL_NOT_VALID_MSG = "Email: %s is not valid";

    public InvalidEmailException(String email) {
        super(String.format(EMAIL_NOT_VALID_MSG, email));
    }
}
