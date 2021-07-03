//package com.example.azureservicebusexample.samples;
//
//import com.rabbitmq.client.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.util.concurrent.TimeoutException;
//
//@Component
//@Slf4j
//public class RabbitMQTest implements ApplicationRunner {
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        this.testSend();
//        this.testReceive();
//    }
//
//    private void testSend() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri("amqp://test:test@localhost:5672/test");
//        Connection conn = factory.newConnection("app:audit component:event-consumer");
//        Channel channel = conn.createChannel();
//
//
//        byte[] messageBodyBytes = "Hello, world!".getBytes();
//        channel.basicPublish("rmq.test.exchange", "", null, messageBodyBytes);
//
//
//        channel.close();
//        conn.close();
//    }
//
//    private void testReceive() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri("amqp://test:test@localhost:5672/test");
//        Connection conn = factory.newConnection("app:audit component:event-consumer");
//        Channel channel = conn.createChannel();
//
//        com.rabbitmq.client.AMQP.Queue.DeclareOk response = channel.queueDeclarePassive("rmq.test.queue");
//
//        channel.basicConsume("rmq.test.queue", false, "myConsumerTag",
//                new DefaultConsumer(channel) {
//                    @Override
//                    public void handleDelivery(String consumerTag,
//                                               Envelope envelope,
//                                               AMQP.BasicProperties properties,
//                                               byte[] body)
//                            throws IOException {
//                        String routingKey = envelope.getRoutingKey();
//                        String contentType = properties.getContentType();
//                        long deliveryTag = envelope.getDeliveryTag();
//                        // (process the message components here ...)
//                        channel.basicAck(deliveryTag, false);
//                        System.out.println(new String(body));
//                    }
//                });
//
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            channel.close();
//            conn.close();
//        }
//    }
//}
