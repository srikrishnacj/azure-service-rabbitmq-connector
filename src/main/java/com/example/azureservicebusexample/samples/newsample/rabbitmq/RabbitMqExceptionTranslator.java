package com.example.azureservicebusexample.samples.newsample.rabbitmq;

import com.example.azureservicebusexample.samples.exception.ConnectionException;
import com.example.azureservicebusexample.samples.exception.ExceptionTranslator;
import com.example.azureservicebusexample.samples.exception.UnknownException;

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
