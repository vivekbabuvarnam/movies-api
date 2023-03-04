package com.backbase.rest.moviesapi.exception;

public class ServiceConsumptionException extends RuntimeException {

    public ServiceConsumptionException(String message) {
        super(message);
    }

    public ServiceConsumptionException(String message, Throwable cause) {
        super(message, cause);
    }
}