package com.example.azureservicebusexample.connector.api;

public interface ConsumerListener {
    boolean consume(String messageId, byte[] body);
}
