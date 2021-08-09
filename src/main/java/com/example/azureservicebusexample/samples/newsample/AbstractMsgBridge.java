package com.example.azureservicebusexample.samples.newsample;

import com.example.azureservicebusexample.samples.newsample.api.MsgConsumer;
import com.example.azureservicebusexample.samples.newsample.api.MsgProducer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractMsgBridge implements MsgBridge {
    private final MsgConsumer consumer;
    private final MsgProducer producer;

    public AbstractMsgBridge(MsgConsumer consumer, MsgProducer producer) {
        this.consumer = consumer;
        this.producer = producer;
    }

    @Override
    public void start() {
        this.producer.start();
        this.consumer.start();
    }

    @Override
    public void stop() {
        this.producer.stop();
        this.consumer.stop();
    }

    @Override
    public void restart() {
        this.stop();
        this.stop();
    }

    @Override
    public MsgBridgeStatus status() {
        return new MsgBridgeStatus(this.producer.status(), this.consumer.status());
    }
}