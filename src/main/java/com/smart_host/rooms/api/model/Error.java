package com.smart_host.rooms.api.model;

/**
 * Represents a error when processing requests.
 */
public class Error {
    private String message;

    public Error(String errorMessage) {
        message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
