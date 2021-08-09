package com.example.azureservicebusexample.samples.exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String msg, Throwable exception) {
        super(msg);
        if (exception != null)
            this.initCause(exception);
    }
}
