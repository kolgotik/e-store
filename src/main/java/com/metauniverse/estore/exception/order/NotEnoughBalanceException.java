package com.metauniverse.estore.exception.order;

public class NotEnoughBalanceException extends RuntimeException {
    private final static String NOT_ENOUGH_BALANCE_MSG = "Not enough balance";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotEnoughBalanceException() {
        super(NOT_ENOUGH_BALANCE_MSG);
    }
}
