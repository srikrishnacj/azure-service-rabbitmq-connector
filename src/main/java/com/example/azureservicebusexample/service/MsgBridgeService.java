package com.example.azureservicebusexample.service;

import com.example.azureservicebusexample.bridge.MsgBridge;
import com.example.azureservicebusexample.bridge.MsgBridgeConfig;
import com.example.azureservicebusexample.bridge.MsgBridgeStatus;
import com.example.azureservicebusexample.config.properties.RabbitMqConsumerProperties;
import com.example.azureservicebusexample.config.properties.ServiceBusConsumerProperties;
import com.example.azureservicebusexample.persistance.JsonFileBasedMsgBridgeConfigRepo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MsgBridgeService {
    private final RabbitMqConsumerProperties rabbitMqConsumerProperties;
    private final ServiceBusConsumerProperties serviceBusConsumerProperties;
    private List<MsgBridge> bridges = new ArrayList<>();

    private MsgBridgeService(RabbitMqConsumerProperties rabbitMqConsumerProperties, ServiceBusConsumerProperties serviceBusConsumerProperties) {
        this.rabbitMqConsumerProperties = rabbitMqConsumerProperties;
        this.serviceBusConsumerProperties = serviceBusConsumerProperties;
    }

    @PostConstruct
    public void init() throws IOException {
        JsonFileBasedMsgBridgeConfigRepo repo = new JsonFileBasedMsgBridgeConfigRepo();

        List<MsgBridgeConfig> msgBridgeConfigs = repo.loadConfig();
        msgBridgeConfigs.stream().forEach(config -> {
            for (int ii = 0; ii < config.getParallel(); ii++) {
                this.bridges.add(startBridge(config.getBridgeId() + "-" + ii, config));
            }
        });
    }

    public List<MsgBridgeStatus> status() {
        return bridges.stream().map(bridge -> bridge.status()).collect(Collectors.toList());
    }

    private MsgBridge startBridge(String name, MsgBridgeConfig config) {
        MsgBridge msgBridge = new MsgBridge(name, config, rabbitMqConsumerProperties, serviceBusConsumerProperties);

        Thread thread = new Thread(() -> {
            msgBridge.start();
        });

        thread.start();

        return msgBridge;
    }
}
