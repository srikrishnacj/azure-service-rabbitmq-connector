package com.example.azureservicebusexample.samples.newsample.rabbitmq.msgconsumer;

import com.example.azureservicebusexample.samples.newsample.rabbitmq.msgproducer.NoopRabbitMqMsgProducerImpl;

public class RabbitMqMsgConsumerMain {
    public static void main(String args[]) {
        NoopRabbitMqMsgProducerImpl producer = new NoopRabbitMqMsgProducerImpl();
        producer.start();

        RabbitMqMsgConsumerConfig config = new RabbitMqMsgConsumerConfig();
        config.setConnectionString("amqp://test:test@localhost:5672/test");
        config.setQueue("inbound.qu");

        RabbitMqMsgConsumerImpl consumer = new RabbitMqMsgConsumerImpl(config);
        consumer.producer(producer);
        consumer.start();
    }
}
