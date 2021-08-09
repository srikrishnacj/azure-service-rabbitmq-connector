package com.example.azureservicebusexample.samples.newsample;

import com.example.azureservicebusexample.samples.newsample.api.MsgConsumerStatus;
import com.example.azureservicebusexample.samples.newsample.api.MsgProducerStatus;
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
