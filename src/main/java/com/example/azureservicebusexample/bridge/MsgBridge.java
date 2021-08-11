package com.example.azureservicebusexample.bridge;

import com.example.azureservicebusexample.bridge.api.MsgConsumer;
import com.example.azureservicebusexample.bridge.api.MsgProducer;
import com.example.azureservicebusexample.bridge.rabbitmq.RabbitMqMsgConsumerImpl;
import com.example.azureservicebusexample.bridge.rabbitmq.RabbitMqMsgProducerImpl;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgConsumerImpl;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgProducerImpl;
import com.example.azureservicebusexample.config.properties.RabbitMqConsumerProperties;
import com.example.azureservicebusexample.config.properties.ServiceBusConsumerProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MsgBridge {
    private final MsgBridgeConfig config;
    private final MsgConsumer consumer;
    private final MsgProducer producer;
    private final String name;

    public MsgBridge(String name, MsgBridgeConfig config, RabbitMqConsumerProperties rabbitMqConsumerProperties, ServiceBusConsumerProperties serviceBusConsumerProperties) {
        this.name = name;
        this.config = config;
        if (config.getSource().equals("SERVICE_BUS")) {
            this.producer = new RabbitMqMsgProducerImpl(config.getRabbitMqConfig());
            this.consumer = new ServiceBusMsgConsumerImpl(config.getServiceBusMsgConfig(), serviceBusConsumerProperties);
            this.consumer.producer(producer);
        } else {
            this.producer = new ServiceBusMsgProducerImpl(config.getServiceBusMsgConfig());
            this.consumer = new RabbitMqMsgConsumerImpl(config.getRabbitMqConfig(), rabbitMqConsumerProperties);
            this.consumer.producer(producer);
        }
    }

    public void start() {
        this.producer.start();
        this.consumer.start();
    }

    public void stop() {
        this.producer.stop();
        this.consumer.stop();
    }

    public void restart() {
        this.stop();
        this.stop();
    }

    public MsgBridgeStatus status() {
        return new MsgBridgeStatus(this.name, this.producer.status(), this.consumer.status());
    }
}