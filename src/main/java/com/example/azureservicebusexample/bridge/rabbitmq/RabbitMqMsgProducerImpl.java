package com.example.azureservicebusexample.bridge.rabbitmq;

import com.example.azureservicebusexample.bridge.api.AbstractMsgProducerImpl;
import com.example.azureservicebusexample.bridge.api.MsgProducerStatus;
import com.example.azureservicebusexample.utils.ExceptionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class RabbitMqMsgProducerImpl extends AbstractMsgProducerImpl {
    private final MsgProducerStatus status = new MsgProducerStatus();
    private final RabbitMqMsgConfig config;
    private Connection conn;
    private Channel channel;

    public RabbitMqMsgProducerImpl(RabbitMqMsgConfig config) {
        super(new RabbitMqExceptionTranslator());
        this.config = config;
    }

    @Override
    protected void startInternal() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(config.getConnectionString());
        this.conn = factory.newConnection("app:audit component:event-consumer");
        this.channel = conn.createChannel();
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

    @Override
    public void sendInternal(String messageId, byte[] message) throws IOException {
        channel.basicPublish(this.config.getExchange(), "", null, message);
    }

    @Override
    public boolean isChannelAvailable() {
        return this.channel != null && this.conn != null;
    }
}