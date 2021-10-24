package com.justanalytics.exception;

public final class UnAccessibleSystemException extends RuntimeException {
    private static final long serialVersionUID = -5220937131925047808L;

    @Override
    public String getMessage() {
        return "Database was not found or was not accessible";
    }

}
