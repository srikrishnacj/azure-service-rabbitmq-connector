package com.example.azureservicebusexample.bridge.api;

import com.example.azureservicebusexample.bridge.rabbitmq.RabbitMqExceptionTranslator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class NoopMsgProducerImpl extends AbstractMsgProducerImpl {
    private final MsgProducerStatus status = new MsgProducerStatus();
    private final int delayInMilliseconds;

    public NoopMsgProducerImpl() {
        super(new RabbitMqExceptionTranslator());
        this.delayInMilliseconds = 0;
    }

    public NoopMsgProducerImpl(int delayInMilliseconds) {
        super(new RabbitMqExceptionTranslator());
        this.delayInMilliseconds = delayInMilliseconds;
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
        try {
            Thread.sleep(this.delayInMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isChannelAvailable() {
        return true;
    }
}