package com.example.azureservicebusexample.samples.newsample.servicebus;

import com.example.azureservicebusexample.samples.exception.ConnectionException;
import com.example.azureservicebusexample.samples.exception.UnknownException;

public class ServiceBusExceptionTranslator {
    public static RuntimeException translate(Throwable exception) {
        String msg = exception.getMessage();
        if (msg.contains("The messaging entity") && msg.contains("could not be found")) {
            return new ConnectionException("Invalid Connection String. Could be connection string is wrong or queue name is wrong", exception);
        } else if (msg.contains("status-code: 401")) {
            return new ConnectionException("Invalid Connection String. Could be connection credentials are invalid", exception);
        } else if (msg.contains("Retries exhausted")) {
            return new ConnectionException("Network Error", exception);
        } else {
            return new UnknownException("Unknown Exception", exception);
        }
    }
}
