package com.backbase.rest.moviesapi.exception;

public class NotUniqueException extends RuntimeException {

    public NotUniqueException(String message) {
        super(message);
    }

    public NotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}