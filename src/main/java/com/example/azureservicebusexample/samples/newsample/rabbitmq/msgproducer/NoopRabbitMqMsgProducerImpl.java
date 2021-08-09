package com.example.azureservicebusexample.samples.newsample.rabbitmq.msgproducer;

import com.example.azureservicebusexample.samples.newsample.api.AbstractMsgProducerImpl;
import com.example.azureservicebusexample.samples.newsample.api.MsgProducerStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class NoopRabbitMqMsgProducerImpl extends AbstractMsgProducerImpl {
    private final MsgProducerStatus status = new MsgProducerStatus();

    public NoopRabbitMqMsgProducerImpl() {
    }

    @Override
    protected void startInternal() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
    }

    @Override
    protected void stopInternal() {
    }

    @Override
    public void sendInternal(String messageId, byte[] message) throws IOException {
        log.info("received msg");
    }

    @Override
    public boolean isChannelAvailable() {
        return true;
    }
}