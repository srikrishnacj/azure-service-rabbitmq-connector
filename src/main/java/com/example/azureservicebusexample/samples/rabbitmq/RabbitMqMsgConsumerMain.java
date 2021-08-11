package com.example.azureservicebusexample.samples.rabbitmq;

import com.example.azureservicebusexample.bridge.api.NoopMsgProducerImpl;
import com.example.azureservicebusexample.bridge.rabbitmq.RabbitMqMsgConfig;
import com.example.azureservicebusexample.bridge.rabbitmq.RabbitMqMsgConsumerImpl;
import com.example.azureservicebusexample.config.properties.RabbitMqConsumerProperties;

public class RabbitMqMsgConsumerMain extends RabbitMqCommonMain {
    public static void main(String args[]) {
        RabbitMqMsgConsumerMain main = new RabbitMqMsgConsumerMain();
        main.run();
    }

    private void run() {
        this.configMicroMeter();

        NoopMsgProducerImpl producer = new NoopMsgProducerImpl(100);
        producer.start();

        RabbitMqMsgConfig config = new RabbitMqMsgConfig();
        config.setConnectionString("amqp://test:test@localhost:5672/test");
        config.setQueue("source.qu");

        RabbitMqMsgConsumerImpl consumer = new RabbitMqMsgConsumerImpl(config, new RabbitMqConsumerProperties());
        consumer.producer(producer);
        consumer.start();
    }
}
