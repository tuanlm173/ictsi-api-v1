package com.justanalytics.exception;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;
    private String status;
    private int code;

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public ExceptionResponse(Date timestamp, String message, String details, String status, int code) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
        this.code = code;
    }
}
