package com.metauniverse.estore.exception;

public class UserWithEmailNotFoundException extends RuntimeException {
    private final static String EMAIL_NOT_FOUND_MSG = "User with email %s not found";

    public UserWithEmailNotFoundException(String email) {
        super(String.format(EMAIL_NOT_FOUND_MSG, email));
    }
}
