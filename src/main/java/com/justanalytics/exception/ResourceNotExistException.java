package com.justanalytics.exception;

public class ResourceNotExistException extends RuntimeException {

    private static final long serialVersionUID = -8027677567823114642L;
    private String message;

    public ResourceNotExistException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
