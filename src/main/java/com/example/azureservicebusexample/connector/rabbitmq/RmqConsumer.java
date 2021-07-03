package com.example.azureservicebusexample.connector.rabbitmq;

import com.example.azureservicebusexample.apiresponse.errorcodes.ConsumerProducerErrorCode;
import com.example.azureservicebusexample.connector.api.Consumer;
import com.example.azureservicebusexample.connector.api.ConsumerListener;
import com.example.azureservicebusexample.exception.NonWebAppException;
import com.example.azureservicebusexample.properties.RmqConf;
import com.example.azureservicebusexample.utils.ExceptionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RmqConsumer implements Consumer {
    private final RmqConf rmqConf;
    private ConsumerListener consumerListener;
    private Connection conn;
    private Channel channel;

    public RmqConsumer(RmqConf rmqConf) {
        this.rmqConf = rmqConf;
    }

    @Override
    public String getName() {
        return "RmqConsumer";
    }

    @Override
    public void validateConf() {
        assertThat(consumerListener).isNotNull();
        assertThat(rmqConf).isNotNull();
        assertThat(rmqConf.getConnectionString()).isNotEmpty();
        assertThat(rmqConf.getQueue()).isNotEmpty();
    }

    @Override
    public void setListener(ConsumerListener listener) {
        this.consumerListener = listener;
    }

    @Override
    public void start() {
        try {
            this.validateConf();

            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(rmqConf.getConnectionString());
            //        conn = factory.newConnection("app:audit component:event-consumer");
            conn = factory.newConnection(UUID.randomUUID().toString());
            channel = conn.createChannel();

            com.rabbitmq.client.AMQP.Queue.DeclareOk response = channel.queueDeclarePassive("rmq.test.queue");

            channel.basicConsume(this.rmqConf.getQueue(), false, "myConsumerTag", messageConsumer());
        } catch (Exception e) {
            throw new NonWebAppException(ConsumerProducerErrorCode.CONSUMER_BOOT_FAILED, e);
        }
    }

    private DefaultConsumer messageConsumer() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                try {
                    String messageId = String.valueOf(envelope.getDeliveryTag());
                    boolean isConsumed = consumerListener.consume(messageId, body);
                    if (isConsumed) {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } else {
                        channel.basicRecover(true);
                    }
                } catch (Exception ex) {
                    log.error("error while consuming msg", ex);
                    channel.basicRecover(true);
                }
            }
        };
    }

    @Override
    public void stop() {
        ExceptionUtil.suppress(() -> channel.close());
        ExceptionUtil.suppress(() -> conn.close());
    }
}
