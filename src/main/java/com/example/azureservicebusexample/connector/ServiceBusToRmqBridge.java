package com.example.azureservicebusexample.connector;

import com.example.azureservicebusexample.connector.api.BridgeType;
import com.example.azureservicebusexample.connector.status.BridgeStatusImpl;
import com.example.azureservicebusexample.connector.rabbitmq.RmqProducer;
import com.example.azureservicebusexample.connector.servicebus.ServiceBusConsumer;
import com.example.azureservicebusexample.properties.BridgeConf;

public class ServiceBusToRmqBridge extends AbstractBridge {
    public ServiceBusToRmqBridge(BridgeConf conf) {
        super(new BridgeStatusImpl(conf.getBridgeId(), BridgeType.INBOUND), new ServiceBusConsumer(conf.getServiceBusConf()), new RmqProducer(conf.getRmqConf()));
    }
}
