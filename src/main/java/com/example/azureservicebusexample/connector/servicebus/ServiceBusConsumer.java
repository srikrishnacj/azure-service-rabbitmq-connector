package com.example.azureservicebusexample.connector.servicebus;

import com.azure.core.amqp.AmqpRetryMode;
import com.azure.core.amqp.AmqpRetryOptions;
import com.azure.core.amqp.AmqpTransportType;
import com.azure.messaging.servicebus.*;
import com.example.azureservicebusexample.apiresponse.errorcodes.ConsumerProducerErrorCode;
import com.example.azureservicebusexample.connector.api.Consumer;
import com.example.azureservicebusexample.connector.api.ConsumerListener;
import com.example.azureservicebusexample.exception.NonWebAppException;
import com.example.azureservicebusexample.properties.ServiceBusConf;
import com.example.azureservicebusexample.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ServiceBusConsumer implements Consumer {
    private final ServiceBusConf serviceBusConf;
    private ServiceBusProcessorClient processorClient;
    private ConsumerListener consumerListener;

    public ServiceBusConsumer(ServiceBusConf serviceBusConf) {
        this.serviceBusConf = serviceBusConf;
    }

    @Override
    public String getName() {
        return "ServiceBusConsumer";
    }

    @Override
    public void validateConf() {
        assertThat(consumerListener).isNotNull();
        assertThat(serviceBusConf).isNotNull();
        assertThat(serviceBusConf.getConnectionString()).isNotEmpty();
        assertThat(serviceBusConf.getQueue()).isNotEmpty();
        Assert.notNull(consumerListener, "Consumer Listener should not be null");
    }

    @Override
    public void setListener(ConsumerListener listener) {
        this.consumerListener = listener;
    }

    @Override
    public void start() {
        try {
            log.info("creating and starting service bus consumer");
            this.validateConf();

            processorClient = this.createClient(this.messageConsumer(), this.errorConsumer());
            processorClient.start();

            log.info("service bus client started");
        } catch (Exception e) {
            NonWebAppException exception = new NonWebAppException(ConsumerProducerErrorCode.CONSUMER_BOOT_FAILED, e);
            throw exception;
        }
    }

    @Override
    public void stop() {
        log.error("stopping and destroying service bus client");
        ExceptionUtil.suppress(() -> {
            this.processorClient.stop();
            this.processorClient.close();
        });
    }

    private java.util.function.Consumer<ServiceBusReceivedMessageContext> messageConsumer() {
        java.util.function.Consumer<ServiceBusReceivedMessageContext> processMessage = messageContext -> {
            try {
                String messageId = messageContext.getMessage().getMessageId();
                byte[] body = messageContext.getMessage().getBody().toBytes();
                boolean isConsumed = consumerListener.consume(messageId, body);
                if (isConsumed) {
                    messageContext.complete();
                } else {
                    System.out.println("Error Unnable to commit Consumed msg from service bus");
                    messageContext.abandon();
                }
            } catch (Exception ex) {
                log.error("unable to consume msg", ex);
                messageContext.abandon();
            }
        };

        return processMessage;
    }

    private java.util.function.Consumer<ServiceBusErrorContext> errorConsumer() {
        java.util.function.Consumer<ServiceBusErrorContext> processError = errorContext -> {
            log.error("error on service bus client namesapce:{} entitypath:{} errorsource:{}", errorContext.getFullyQualifiedNamespace(), errorContext.getEntityPath(), errorContext.getErrorSource(), errorContext.getException());
        };

        return processError;
    }

    private ServiceBusProcessorClient createClient(java.util.function.Consumer<ServiceBusReceivedMessageContext> messageConsumer, java.util.function.Consumer<ServiceBusErrorContext> errorConsumer) {
        AmqpRetryOptions retryOptions = new AmqpRetryOptions();
        retryOptions.setMaxRetries(1);
        retryOptions.setDelay(Duration.ofSeconds(10));
        retryOptions.setMode(AmqpRetryMode.EXPONENTIAL);

        return new ServiceBusClientBuilder()
                .connectionString(serviceBusConf.getConnectionString())
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .retryOptions(retryOptions)
                .processor()
                .queueName(serviceBusConf.getQueue())
                .processMessage(messageConsumer)
                .processError(errorConsumer)
                .maxConcurrentCalls(1)
                .disableAutoComplete()
                .buildProcessorClient();
    }
}