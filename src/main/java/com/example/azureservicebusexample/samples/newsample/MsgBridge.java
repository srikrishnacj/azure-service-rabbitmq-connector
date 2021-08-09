package com.example.azureservicebusexample.samples.newsample;

import com.example.azureservicebusexample.connector.api.BridgeStatus;

public interface MsgBridge {
    void start();
    void stop();
    void restart();
    MsgBridgeStatus status();
}
