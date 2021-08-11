package com.example.azureservicebusexample.samples.raw;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ServiceBusConsumeMain {
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
        Consumer<ServiceBusReceivedMessageContext> processMessage = messageContext -> {
            try {
                String msg = new String(messageContext.getMessage().getBody().toBytes());
                System.out.println(msg);
                // other message processing code
                messageContext.complete();
            } catch (Exception ex) {
                messageContext.abandon();
            }
        };

        // Sample code that gets called if there's an error
        Consumer<ServiceBusErrorContext> processError = errorContext -> {
            System.err.println("Error occurred while receiving message: " + errorContext.getException());
        };

        // create the processor client via the builder and its sub-builder
        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .connectionString("Endpoint=sb://servicebustestcj.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=LcYMwDK3sweCYEp130j+4hPRRudEOUUp4Lm7uPbaUY8=")
                .processor()
                .queueName("testqueue")
                .processMessage(processMessage)
                .processError(processError)
                .disableAutoComplete()
                .buildProcessorClient();

        // Starts the processor in the background and returns immediately
        processorClient.start();
    }
}
