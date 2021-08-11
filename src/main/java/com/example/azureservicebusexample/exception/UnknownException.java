package com.example.azureservicebusexample.exception;

public class UnknownException extends RuntimeException {
    public UnknownException(String msg, Throwable exception) {
        super(msg);
        this.initCause(exception);
    }
}
