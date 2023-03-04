package com.backbase.rest.moviesapi.exception;

public class InvalidJwtToken extends RuntimeException {

    public InvalidJwtToken(String message) {
        super(message);
    }

    public InvalidJwtToken(String message, Throwable cause) {
        super(message, cause);
    }
}