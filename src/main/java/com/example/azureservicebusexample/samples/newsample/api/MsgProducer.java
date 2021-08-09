package com.example.azureservicebusexample.samples.newsample.api;

public interface MsgProducer {
    String getName();

    MsgProducerStatus status();

    void start();

    void stop();

    boolean send(String messageId, byte[] message);
}