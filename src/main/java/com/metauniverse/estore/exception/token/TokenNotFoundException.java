package com.metauniverse.estore.exception.token;

public final class TokenNotFoundException extends RuntimeException{
    private final static String TOKEN_NOT_FOUND_MSG = "Token not found";

    public TokenNotFoundException() {
        super(TOKEN_NOT_FOUND_MSG);
    }
}
