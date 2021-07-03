package com.example.azureservicebusexample.service;

import com.example.azureservicebusexample.connector.RmqToServiceBusBridge;
import com.example.azureservicebusexample.connector.ServiceBusToRmqBridge;
import com.example.azureservicebusexample.connector.api.Bridge;
import com.example.azureservicebusexample.connector.api.BridgeStatus;
import com.example.azureservicebusexample.properties.BridgeConf;
import com.example.azureservicebusexample.utils.JsonUtil;
import com.google.common.reflect.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BridgeService {

    private List<Bridge> bridges = new LinkedList<>();

    @PostConstruct
    public void init() throws IOException {
        Type REVIEW_TYPE = new TypeToken<ArrayList<BridgeConf>>() {
        }.getType();
        List<BridgeConf> bridgeConfList = (List<BridgeConf>) JsonUtil.loadFromClasspath("inbound-bridge.json", REVIEW_TYPE);
        for (BridgeConf conf : bridgeConfList) {
            if (conf.getParallel() > 1) {
                for (int ii = 0; ii < conf.getParallel(); ii++) {
                    bridges.add(new ServiceBusToRmqBridge(conf.clone(ii)));
                }
            } else {
                bridges.add(new ServiceBusToRmqBridge(conf));
            }
        }
        bridgeConfList = (List<BridgeConf>) JsonUtil.loadFromClasspath("outbound-bridge.json", REVIEW_TYPE);
        for (BridgeConf conf : bridgeConfList) {
            if (conf.getParallel() > 1) {
                for (int ii = 0; ii < conf.getParallel(); ii++) {
                    bridges.add(new RmqToServiceBusBridge(conf.clone(ii)));
                }
            } else {
                bridges.add(new RmqToServiceBusBridge(conf));
            }
        }

        bridges.forEach(bridge -> bridge.start());
    }

    private void processBridgeConf(BridgeConf bridgeConf) {

    }

    public List<BridgeStatus> status() {
        return bridges.stream().map(bridge -> bridge.status()).collect(Collectors.toList());
    }
}
