package com.example.azureservicebusexample.connector.rabbitmq;

import com.example.azureservicebusexample.apiresponse.errorcodes.ConsumerProducerErrorCode;
import com.example.azureservicebusexample.connector.api.Producer;
import com.example.azureservicebusexample.exception.NonWebAppException;
import com.example.azureservicebusexample.properties.RmqConf;
import com.example.azureservicebusexample.utils.ExceptionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.*;


import java.io.IOException;

@Slf4j
public class RmqProducer implements Producer {
    private RmqConf rmqConf;
    private Connection conn;
    private Channel channel;

    public RmqProducer(RmqConf rmqConf) {
        this.rmqConf = rmqConf;
    }

    @Override
    public boolean send(String messageId, byte[] message) {
        try {
            channel.basicPublish(this.rmqConf.getExchange(), "", null, message);
            return true;
        } catch (IOException e) {
            log.error("Error while sending msg", e);
            return false;
        }
    }

    @Override
    public String getName() {
        return "RmqProducer";
    }

    @Override
    public void validateConf() {
        assertThat(rmqConf).isNotNull();
        assertThat(rmqConf.getConnectionString()).isNotEmpty();
        assertThat(rmqConf.getExchange()).isNotEmpty();
    }

    @Override
    public void start() {
        try {
            this.validateConf();

            log.info("creating and starting rmq producer");
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(rmqConf.getConnectionString());
            conn = factory.newConnection("app:audit component:event-consumer");
            channel = conn.createChannel();
            log.info("successfully created and started rmq producer");
        } catch (Exception e) {
            NonWebAppException exception = new NonWebAppException(ConsumerProducerErrorCode.PRODUCER_BOOT_FAILED, e);
            throw exception;
        }
    }

    @Override
    public void stop() {
        log.info("stopping and destroying rmq producer");
        ExceptionUtil.suppress(() -> channel.close());
        ExceptionUtil.suppress(() -> conn.close());
        channel = null;
        conn = null;
    }
}
