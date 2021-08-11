package com.example.azureservicebusexample.bridge;

import com.example.azureservicebusexample.bridge.api.MsgConsumerStatus;
import com.example.azureservicebusexample.bridge.api.MsgProducerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MsgBridgeStatus {
    private MsgProducerStatus producerStatus;
    private MsgConsumerStatus consumerStatus;
}
