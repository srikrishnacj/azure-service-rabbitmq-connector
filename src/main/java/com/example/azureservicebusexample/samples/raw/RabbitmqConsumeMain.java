package com.example.azureservicebusexample.samples.raw;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitmqConsumeMain {
    public static void main(String args[]) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://test:test@localhost:5672/test");
        Connection conn = factory.newConnection("app:audit component:event-consumer");
        Channel channel = conn.createChannel();

        com.rabbitmq.client.AMQP.Queue.DeclareOk response = channel.queueDeclarePassive("rmq.test.queue");

        channel.basicConsume("rmq.test.queue", false, "myConsumerTag",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body)
                            throws IOException {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        long deliveryTag = envelope.getDeliveryTag();
                        // (process the message components here ...)
                        channel.basicAck(deliveryTag, false);
                        System.out.println(new String(body));
                    }
                });
    }
}
