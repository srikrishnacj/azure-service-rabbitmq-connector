package com.example.azureservicebusexample.bridge.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MsgProducerStatus {
    private boolean isRunning;
    private String lastKnownException;
}