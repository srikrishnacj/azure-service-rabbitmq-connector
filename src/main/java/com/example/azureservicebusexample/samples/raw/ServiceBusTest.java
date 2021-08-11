//package com.example.azureservicebusexample.samples;
//
//import com.azure.core.amqp.AmqpTransportType;
//import com.azure.messaging.servicebus.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.function.Consumer;
//
//@Component
//@Slf4j
//public class ServiceBusTest implements ApplicationRunner {
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
////        this.testSender();
////        this.testReceiver();
//    }
//
//    private void testSender() {
//        ServiceBusSenderClient sender = new ServiceBusClientBuilder()
//                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
//                .connectionString("Endpoint=sb://servicebustestcj.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=LcYMwDK3sweCYEp130j+4hPRRudEOUUp4Lm7uPbaUY8=")
//                .sender()
//                .queueName("testqueue")
//                .buildClient();
//
//        List<ServiceBusMessage> messages = new LinkedList<>();
//
//        for (int ii = 0; ii < 1000; ii++) {
//            ServiceBusMessage msg = new ServiceBusMessage("Hello world").setMessageId((ii % 10) + "");
//            messages.add(msg);
//        }
//
//        sender.sendMessages(messages);
//
//        // When you are done using the sender, dispose of it.
//        sender.close();
//    }
//
//    private void testReceiver() {
//        // Sample code that processes a single message
//        Consumer<ServiceBusReceivedMessageContext> processMessage = messageContext -> {
//            try {
//                System.out.println(messageContext.getMessage().getMessageId() + " : " + messageContext.getMessage().getBody());
//                // other message processing code
//                messageContext.complete();
//            } catch (Exception ex) {
//                messageContext.abandon();
//            }
//        };
//
//        // Sample code that gets called if there's an error
//        Consumer<ServiceBusErrorContext> processError = errorContext -> {
//            System.err.println("Error occurred while receiving message: " + errorContext.getException());
//        };
//
//        // create the processor client via the builder and its sub-builder
//        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
//                .connectionString("Endpoint=sb://servicebustestcj.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=LcYMwDK3sweCYEp130j+4hPRRudEOUUp4Lm7uPbaUY8=")
//                .processor()
//                .queueName("testqueue")
//                .processMessage(processMessage)
//                .processError(processError)
//                .disableAutoComplete()
//                .buildProcessorClient();
//
//        // Starts the processor in the background and returns immediately
//        processorClient.start();
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            processorClient.stop();
//        }
//    }
//}
