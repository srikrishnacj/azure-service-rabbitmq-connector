package com.example.azureservicebusexample.bridge.servicebus;

import com.azure.core.amqp.AmqpRetryMode;
import com.azure.core.amqp.AmqpRetryOptions;
import com.azure.core.amqp.AmqpTransportType;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.example.azureservicebusexample.bridge.api.AbstractMsgConsumerImpl;
import com.example.azureservicebusexample.config.properties.ServiceBusConsumerProperties;
import com.example.azureservicebusexample.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.function.Consumer;

@Slf4j
public class ServiceBusMsgConsumerImpl extends AbstractMsgConsumerImpl {
    private final ServiceBusMsgConfig config;
    private final ServiceBusConsumerProperties properties;
    private ServiceBusProcessorClient consumer;

    public ServiceBusMsgConsumerImpl(ServiceBusMsgConfig config, ServiceBusConsumerProperties properties) {
        super(new ServiceBusExceptionTranslator());
        this.config = config;
        this.properties = properties;
    }

    @Override
    protected void startInternal() throws Exception {
        Consumer<ServiceBusReceivedMessageContext> processMessage = messageContext -> {
            byte[] bytes = messageContext.getMessage().getBody().toBytes();
            String messageId = messageContext.getMessage().getMessageId();
            boolean isSent = super.onMessage(messageId, bytes);
            if (isSent) {
                messageContext.complete();
            } else {
                messageContext.abandon();
            }
        };

        Consumer<ServiceBusErrorContext> processError = errorContext -> {
            RuntimeException exception = exceptionTranslator.translate(errorContext.getException());
            log.error("error while consuming msg", exception);
            this.stop(exception);
        };

        AmqpRetryOptions retryOptions = new AmqpRetryOptions();
        retryOptions.setMaxRetries(this.properties.getMaxRetries());
        retryOptions.setDelay(Duration.ofSeconds(this.properties.getRetryDelayInSecs()));
        retryOptions.setMode(AmqpRetryMode.FIXED);

        // create the processor client via the builder and its sub-builder
        this.consumer = new ServiceBusClientBuilder()
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .retryOptions(retryOptions)
                .connectionString(config.getConnectionString())
                .processor()
                .maxConcurrentCalls(this.properties.getMaxConcurrentCalls())
                .prefetchCount(this.properties.getPrefetchCount())
                .queueName(config.getQueue())
                .processMessage(processMessage)
                .processError(processError)
                .disableAutoComplete()
                .buildProcessorClient();

        // Starts the processor in the background and returns immediately
        consumer.start();
    }

    @Override
    protected void stopInternal() {
        ExceptionUtil.suppress(() -> {
            this.consumer.close();
            this.consumer = null;
        });
    }
}
