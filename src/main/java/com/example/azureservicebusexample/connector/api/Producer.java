package com.example.azureservicebusexample.connector.api;

public interface Producer {
    String getName();
    void validateConf();
    void start();
    void stop();
    boolean send(String messageId, byte[] message);
}