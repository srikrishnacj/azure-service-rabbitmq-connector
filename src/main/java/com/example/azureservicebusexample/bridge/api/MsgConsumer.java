package com.example.azureservicebusexample.bridge.api;

public interface MsgConsumer {
    String getName();

    MsgConsumerStatus status();

    void producer(MsgProducer producer);

    void start();

    void stop();
}
