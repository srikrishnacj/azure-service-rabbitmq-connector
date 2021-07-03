package com.example.azureservicebusexample.samples;

import com.azure.messaging.servicebus.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ServiceBusReceiverMain {
    public static void main(String args[]) {
//        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
//        root.setLevel(ch.qos.logback.classic.Level.OFF);

        ExecutorService es = Executors.newFixedThreadPool(4);
        es.submit(new Runnable() {
            @Override
            public void run() {
                consume();
            }
        });
    }

    private static void consume() {
        String CONNECTION_STRING = "Endpoint=sb://servicebustestcj.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=DCmTOnIFZyEqUUQhHIGAtLmRgLyfgNn0YpKraP8/L5c=";
        String QUEUE_NAME = "fromazure";

        // Sample code that gets called if there's an error
        Consumer<ServiceBusErrorContext> processError = errorContext -> {
            System.err.println("Error occurred while receiving message: " + errorContext.getException());
        };

        // create the processor client via the builder and its sub-builder
        ServiceBusReceiverClient client = new ServiceBusClientBuilder()
                .connectionString(CONNECTION_STRING)
                .receiver()
                .queueName(QUEUE_NAME)
                .disableAutoComplete()
                .buildClient();
        System.out.println(client.getSessionState());
        System.out.println();
    }
}
