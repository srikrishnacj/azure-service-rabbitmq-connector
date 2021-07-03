package com.example.azureservicebusexample.connector.servicebus;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.example.azureservicebusexample.connector.api.Producer;
import com.example.azureservicebusexample.properties.ServiceBusConf;
import com.example.azureservicebusexample.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ServiceBusProducer implements Producer {
    private final ServiceBusConf serviceBusConf;
    private ServiceBusSenderClient sender;

    public ServiceBusProducer(ServiceBusConf serviceBusConf) {
        this.serviceBusConf = serviceBusConf;
    }

    @Override
    public boolean send(String messageId, byte[] message) {
        try {
            ServiceBusMessage msg = new ServiceBusMessage(message).setMessageId(messageId);
            this.sender.sendMessage(msg);
            return true;
        } catch (Exception e) {
            log.error("unable to send msg", e);
            return false;
        }
    }

    @Override
    public String getName() {
        return "ServiceBusProducer";
    }

    @Override
    public void validateConf() {
        assertThat(serviceBusConf).isNotNull();
        assertThat(serviceBusConf.getConnectionString()).isNotEmpty();
        assertThat(serviceBusConf.getQueue()).isNotEmpty();
    }

    @Override
    public void start() {
        this.validateConf();
        this.sender = new ServiceBusClientBuilder()
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .connectionString(serviceBusConf.getConnectionString())
                .sender()
                .queueName(serviceBusConf.getQueue())
                .buildClient();
    }

    @Override
    public void stop() {
        ExceptionUtil.suppress(() -> {
            this.sender.close();
        });
    }
}
