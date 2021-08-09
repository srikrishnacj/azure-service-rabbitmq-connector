package com.example.azureservicebusexample.samples.newsample.servicebus.msgconsumer;

import com.example.azureservicebusexample.samples.newsample.api.MsgConsumer;
import com.example.azureservicebusexample.samples.newsample.api.MsgProducer;
import com.example.azureservicebusexample.samples.newsample.rabbitmq.msgproducer.NoopRabbitMqMsgProducerImpl;
import com.example.azureservicebusexample.samples.newsample.servicebus.msgproducer.ServiceBusMsgProducerConfig;
import com.example.azureservicebusexample.samples.newsample.servicebus.msgproducer.ServiceBusMsgProducerImpl;
import com.example.azureservicebusexample.utils.IdGenerator;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceBusMsgConsumerMain {
    public static void main(String args[]) {
        ServiceBusMsgConsumerConfig config = new ServiceBusMsgConsumerConfig();
        config.setFriendlyName("Test");
        config.setConnectionString("Endpoint=sb://servicebustestcj.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=DvLIR9UXArdqBwu91+VydqOxuTVY9SXM0JwcZtf/oLA=");
        config.setQueueName("testqueue");

        MsgConsumer consumer = new ServiceBusMsgConsumerImpl(config);
        NoopRabbitMqMsgProducerImpl producer = new NoopRabbitMqMsgProducerImpl();
        producer.start();
        consumer.producer(producer);
        consumer.start();
        health(consumer);
    }

    private static void health(MsgConsumer consumer) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(consumer.status());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
