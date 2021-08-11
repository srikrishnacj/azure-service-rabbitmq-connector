package com.example.azureservicebusexample.bridge.servicebus;

import com.azure.core.amqp.AmqpRetryMode;
import com.azure.core.amqp.AmqpRetryOptions;
import com.azure.core.amqp.AmqpTransportType;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.example.azureservicebusexample.bridge.api.AbstractMsgProducerImpl;
import com.example.azureservicebusexample.bridge.api.MsgProducerStatus;
import com.example.azureservicebusexample.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class ServiceBusMsgProducerImpl extends AbstractMsgProducerImpl {
    private final MsgProducerStatus status = new MsgProducerStatus();
    private final ServiceBusMsgConfig config;
    private ServiceBusSenderClient sender;

    public ServiceBusMsgProducerImpl(ServiceBusMsgConfig config) {
        super(new ServiceBusExceptionTranslator());
        this.config = config;
    }

    @Override
    protected void startInternal() {
        AmqpRetryOptions retryOptions = new AmqpRetryOptions();
        retryOptions.setMaxRetries(1);
        retryOptions.setDelay(Duration.ofSeconds(3));
        retryOptions.setMode(AmqpRetryMode.FIXED);

        this.sender = new ServiceBusClientBuilder()
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .retryOptions(retryOptions)
                .connectionString(this.config.getConnectionString())
                .sender()
                .queueName(this.config.getQueue())
                .buildClient();
    }

    @Override
    protected void stopInternal() {
        ExceptionUtil.suppress(() -> {
            this.sender.close();
        });
        this.sender = null;
    }

    @Override
    public void sendInternal(String messageId, byte[] message) throws Exception {
        this.sender.sendMessage(new ServiceBusMessage(message));
    }

    @Override
    public boolean isChannelAvailable() {
        return this.sender != null;
    }
}
