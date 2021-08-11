package com.example.azureservicebusexample.samples.raw;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.google.gson.Gson;
import io.codearte.jfairy.Fairy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceBusProduceMain {
    public static void main(String args[]) {

//        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
//        root.setLevel(ch.qos.logback.classic.Level.OFF);

        ExecutorService es = Executors.newFixedThreadPool(10);
        es.submit(new Runnable() {
            @Override
            public void run() {
                send();
            }
        });
    }

    private static void send() {
        Gson gson = new Gson();

        ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .connectionString("Endpoint=sb://servicebustestcj.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=LcYMwDK3sweCYEp130j+4hPRRudEOUUp4Lm7uPbaUY8=")
                .sender()
                .queueName("testqueue")
                .buildClient();

        for (int ii = 0; ii < 10_00_000; ii++) {
//            String msg = "Message from RabbitMQ Producer: " + ii;
            String msg = gson.toJson(Fairy.create().person());
            ServiceBusMessage smsg = new ServiceBusMessage(msg).setMessageId(ii + "");
            sender.sendMessage(smsg);
//            ThreadUtil.sleep(100);
//            System.exit(0);
        }

        // When you are done using the sender, dispose of it.
        sender.close();
    }
}
