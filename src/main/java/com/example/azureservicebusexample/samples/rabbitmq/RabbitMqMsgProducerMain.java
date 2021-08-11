package com.example.azureservicebusexample.samples.rabbitmq;

import com.example.azureservicebusexample.bridge.api.MsgProducer;
import com.example.azureservicebusexample.bridge.rabbitmq.RabbitMqMsgConfig;
import com.example.azureservicebusexample.bridge.rabbitmq.RabbitMqMsgProducerImpl;
import com.example.azureservicebusexample.utils.IdGenerator;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RabbitMqMsgProducerMain extends RabbitMqCommonMain{
    public static void main(String args[]) {
        new RabbitMqMsgProducerMain().run();
    }

    private void run() {
        this.configMicroMeter();

        RabbitMqMsgConfig config = new RabbitMqMsgConfig();
        config.setConnectionString("amqp://test:test@localhost:5672/test");
        config.setExchange("source.ex");

        MsgProducer producer = new RabbitMqMsgProducerImpl(config);
        producer.start();

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                producer.send(IdGenerator.id(), IdGenerator.id().getBytes(StandardCharsets.UTF_8));
                if (producer.status().isRunning() == false) {
                    System.exit(0);
                }
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }
}
