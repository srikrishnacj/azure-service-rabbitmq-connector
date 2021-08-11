package com.example.azureservicebusexample.bridge.rabbitmq;

import com.example.azureservicebusexample.exception.ConnectionException;
import com.example.azureservicebusexample.exception.ExceptionTranslator;
import com.example.azureservicebusexample.exception.UnknownException;

public class RabbitMqExceptionTranslator implements ExceptionTranslator {
    public RuntimeException translate(Throwable exception) {
        String msg = exception.getMessage();
        if (msg.contains("no exchange")) {
            return new ConnectionException("Invalid Exchange Name", exception);
        } else {
            return new UnknownException("Unknown Exception", exception);
        }
    }
}
