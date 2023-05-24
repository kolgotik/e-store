package com.metauniverse.estore.exception.token;

import java.time.LocalDateTime;

public final class TokenExpiredException extends RuntimeException {
    private final static String TOKEN_EXPIRED_MSG = "Token expired";
    public TokenExpiredException() {
        super(TOKEN_EXPIRED_MSG);
    }
}
