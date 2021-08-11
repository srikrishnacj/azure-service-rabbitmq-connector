package com.example.azureservicebusexample.samples.raw;

import com.example.azureservicebusexample.utils.ThreadUtil;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.codearte.jfairy.Fairy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitmqProduceMain {
    public static void main(String args[]) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        Gson gson = new Gson();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://test:test@localhost:5672/test");
        Connection conn = factory.newConnection("app:audit component:event-consumer");
        Channel channel = conn.createChannel();

        for (int ii = 0; ii < 10_00_000; ii++) {
//            String msg = "Message from RabbitMQ Producer: " + ii;
            String msg = gson.toJson(Fairy.create().person());

            byte[] messageBodyBytes = msg.getBytes();
            channel.basicPublish("rmq.test.exchange", "", null, messageBodyBytes);
            System.out.println("Message Produced: " + msg);
            ThreadUtil.sleep(5);
        }

        channel.close();
        conn.close();
    }
}
