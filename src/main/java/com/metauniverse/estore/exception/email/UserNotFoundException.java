package com.metauniverse.estore.exception.email;

public final class UserNotFoundException extends RuntimeException {
    private final static String USER_NOT_FOUND_MSG = "User not found";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MSG);
    }
}
