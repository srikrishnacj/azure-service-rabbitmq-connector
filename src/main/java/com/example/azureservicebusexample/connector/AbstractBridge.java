package com.example.azureservicebusexample.connector;

import com.example.azureservicebusexample.connector.api.*;
import com.example.azureservicebusexample.connector.status.BridgeStatusImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractBridge implements Bridge {
    private final Consumer consumer;
    private final Producer producer;
    private final BridgeStatusImpl bridgeStatus;

    public AbstractBridge(BridgeStatusImpl bridgeStatus, Consumer consumer, Producer producer) {
        this.consumer = consumer;
        this.producer = producer;
        this.bridgeStatus = bridgeStatus;
        this.bridgeStatus.setConsumerName(consumer.getName());
        this.bridgeStatus.setProducerName(producer.getName());
    }

    @Override
    public void start() {
        this.consumer.setListener(new ConsumerListener() {
            @Override
            public boolean consume(String messageId, byte[] body) {
                return producer.send(messageId, body);
            }
        });

        try {
            this.producer.validateConf();
        } catch (Exception e) {
            log.error("error while validating producer", e);
        }

        try {
            this.consumer.validateConf();
        } catch (Exception e) {
            log.error("error while validating consumer", e);
        }

        try {
            this.producer.start();
        } catch (Exception e) {
            log.error("error while starting producer", e);
        }

        try {
            this.consumer.start();
        } catch (Exception e) {
            log.error("error while starting consumer", e);
        }
    }

    @Override
    public void stop() {
        this.consumer.stop();
        this.producer.stop();
    }

    @Override
    public void restart() {
        this.stop();
        this.status();
    }

    @Override
    public BridgeStatus status() {
        return this.bridgeStatus;
    }
}
