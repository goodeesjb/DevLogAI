package com.devlogai.backend.common.exception;

public class ExternalApiException extends RuntimeException {

    public ExternalApiException(String message) {
        super(message);
    }
}