package com.example.azureservicebusexample.samples.servicebus;

import com.example.azureservicebusexample.bridge.api.MsgConsumer;
import com.example.azureservicebusexample.bridge.api.NoopMsgProducerImpl;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgConfig;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgConsumerImpl;
import com.example.azureservicebusexample.config.properties.ServiceBusConsumerProperties;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceBusMsgConsumerMain {
    public static void main(String args[]) {
        ServiceBusMsgConfig config = new ServiceBusMsgConfig();
        config.setConnectionString("Endpoint=sb://servicebustestcj.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=DvLIR9UXArdqBwu91+VydqOxuTVY9SXM0JwcZtf/oLA=");
        config.setQueue("testqueue");

        MsgConsumer consumer = new ServiceBusMsgConsumerImpl(config, new ServiceBusConsumerProperties());
        NoopMsgProducerImpl producer = new NoopMsgProducerImpl();
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
