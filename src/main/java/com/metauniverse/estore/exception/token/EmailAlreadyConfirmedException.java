package com.metauniverse.estore.exception.token;

public class EmailAlreadyConfirmedException extends RuntimeException {
    private final static String EMAIL_ALREADY_CONFIRMED_MSG = "Email already confirmed";

    public EmailAlreadyConfirmedException() {
        super(EMAIL_ALREADY_CONFIRMED_MSG);
    }
}
