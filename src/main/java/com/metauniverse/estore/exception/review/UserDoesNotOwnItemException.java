package com.metauniverse.estore.exception.review;

public class UserDoesNotOwnItemException extends RuntimeException{
    private final static String ERROR_MSG = "User does not own the item";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public UserDoesNotOwnItemException() {
        super(ERROR_MSG);
    }
}
