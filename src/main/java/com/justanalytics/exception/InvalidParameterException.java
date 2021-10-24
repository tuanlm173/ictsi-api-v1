package com.justanalytics.exception;

public final class InvalidParameterException extends RuntimeException {

    private static final long serialVersionUID = 4564519489196167590L;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public InvalidParameterException(String message) {
        this.message = message;
    }
}
