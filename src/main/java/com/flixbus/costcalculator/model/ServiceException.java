package com.flixbus.costcalculator.model;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final boolean isRetryable;

    public ServiceException(String message) {
        this(message, null);
    }

    public ServiceException(String message, Throwable cause) {
        this(message, cause, false);
    }

    public ServiceException(String message, Throwable cause, boolean isRetryable) {
        super(message, cause);
        this.isRetryable = isRetryable;
    }
}
