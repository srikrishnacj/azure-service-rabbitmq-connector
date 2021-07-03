package com.example.azureservicebusexample.connector.status;

import com.example.azureservicebusexample.connector.api.BridgeStatus;
import com.example.azureservicebusexample.connector.api.BridgeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BridgeStatusImpl implements BridgeStatus {
    private String bridgeId;
    private BridgeType type;
    private String producerName;
    private String consumerName;

    public BridgeStatusImpl(String bridgeId, BridgeType type) {
        this.bridgeId = bridgeId;
        this.type = type;
    }
}
