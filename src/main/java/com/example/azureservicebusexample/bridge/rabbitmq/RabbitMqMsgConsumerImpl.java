package com.example.azureservicebusexample.bridge.rabbitmq;

import com.example.azureservicebusexample.bridge.api.AbstractMsgConsumerImpl;
import com.example.azureservicebusexample.config.properties.RabbitMqConsumerProperties;
import com.example.azureservicebusexample.utils.ExceptionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class RabbitMqMsgConsumerImpl extends AbstractMsgConsumerImpl {
    private final RabbitMqMsgConfig config;
    private final RabbitMqConsumerProperties properties;
    private Connection conn;
    private Channel channel;

    public RabbitMqMsgConsumerImpl(RabbitMqMsgConfig config, RabbitMqConsumerProperties properties) {
        super(new RabbitMqExceptionTranslator());
        this.config = config;
        this.properties = properties;
    }

    @Override
    protected void startInternal() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(this.config.getConnectionString());
        this.conn = factory.newConnection("app:audit component:event-consumer");
        this.channel = conn.createChannel();
        this.channel.basicQos(this.properties.getPrefetch());

        com.rabbitmq.client.AMQP.Queue.DeclareOk response = channel.queueDeclarePassive(this.config.getQueue());

        this.channel.basicConsume(this.config.getQueue(), false, "myConsumerTag",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body)
                            throws IOException {
                        String messageId = String.valueOf(envelope.getDeliveryTag());
                        boolean isConsumed = onMessage(messageId, body);
                        if (isConsumed) {
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        } else {
                            channel.basicRecover(true);
                        }
                    }
                });
    }

    @Override
    protected void stopInternal() {
        ExceptionUtil.suppress(() -> {
            this.channel.close();
        });
        ExceptionUtil.suppress(() -> {
            this.conn.close();
        });
        this.channel = null;
        this.conn = null;
    }
}
