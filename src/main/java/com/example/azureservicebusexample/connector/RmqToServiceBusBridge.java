package com.example.azureservicebusexample.connector;

import com.example.azureservicebusexample.connector.api.BridgeType;
import com.example.azureservicebusexample.connector.status.BridgeStatusImpl;
import com.example.azureservicebusexample.connector.rabbitmq.RmqConsumer;
import com.example.azureservicebusexample.connector.servicebus.ServiceBusProducer;
import com.example.azureservicebusexample.properties.BridgeConf;

public class RmqToServiceBusBridge extends AbstractBridge {
    public RmqToServiceBusBridge(BridgeConf conf) {
        super(new BridgeStatusImpl(conf.getBridgeId(), BridgeType.OUTBOUND), new RmqConsumer(conf.getRmqConf()), new ServiceBusProducer(conf.getServiceBusConf()));
    }
}
