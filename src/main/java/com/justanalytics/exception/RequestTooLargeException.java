package com.justanalytics.exception;

public class RequestTooLargeException extends RuntimeException {
    private static final long serialVersionUID = -5220937131925047808L;
    private String message;

    public RequestTooLargeException(String message) {this.message = message;}

    @Override
    public String getMessage() {
        return message;
    }
}