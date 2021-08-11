package com.example.azureservicebusexample.samples.servicebus;

import com.example.azureservicebusexample.bridge.api.MsgProducer;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgConfig;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgProducerImpl;
import com.example.azureservicebusexample.utils.IdGenerator;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceBusMsgProducerMain {
    public static void main(String args[]) {
        ServiceBusMsgConfig config = new ServiceBusMsgConfig();
        config.setConnectionString("Endpoint=sb://servicebustestcj.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=DvLIR9UXArdqBwu91+VydqOxuTVY9SXM0JwcZtf/oLA=");
        config.setQueue("testqueue");

        MsgProducer msgProducer = new ServiceBusMsgProducerImpl(config);
        msgProducer.start();

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                msgProducer.send(IdGenerator.id(), IdGenerator.id().getBytes(StandardCharsets.UTF_8));
                System.out.println(msgProducer.status());
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }
}
