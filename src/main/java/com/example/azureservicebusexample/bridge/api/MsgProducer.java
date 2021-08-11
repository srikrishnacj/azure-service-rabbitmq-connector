package com.example.azureservicebusexample.bridge.api;

public interface MsgProducer {
    String getName();

    MsgProducerStatus status();

    void start();

    void stop();

    boolean send(String messageId, byte[] message);
}