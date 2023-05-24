package com.metauniverse.estore.exception.email;

public class EmailNotConfirmedException extends RuntimeException {
    private final static String EMAIL_NOT_CONFIRMED_MSG = "Email is not confirmed";

    public EmailNotConfirmedException() {
        super(EMAIL_NOT_CONFIRMED_MSG);
    }
}
