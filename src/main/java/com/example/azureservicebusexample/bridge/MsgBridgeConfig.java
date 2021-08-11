package com.example.azureservicebusexample.bridge;

import com.example.azureservicebusexample.bridge.rabbitmq.RabbitMqMsgConfig;
import com.example.azureservicebusexample.bridge.servicebus.ServiceBusMsgConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MsgBridgeConfig {
    private String bridgeId;
    private String source;
    private int parallel;
    private RabbitMqMsgConfig rabbitMqConfig;
    private ServiceBusMsgConfig serviceBusMsgConfig;
}
