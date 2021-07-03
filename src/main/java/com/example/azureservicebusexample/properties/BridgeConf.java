package com.example.azureservicebusexample.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BridgeConf {
    private String bridgeId;
    private int parallel;
    private RmqConf rmqConf;
    private ServiceBusConf serviceBusConf;

    public BridgeConf clone(int id) {
        BridgeConf other = new BridgeConf();
        other.bridgeId = bridgeId + "-" + id;
        other.parallel = parallel;
        other.rmqConf = rmqConf;
        other.serviceBusConf = serviceBusConf;
        return other;
    }
}