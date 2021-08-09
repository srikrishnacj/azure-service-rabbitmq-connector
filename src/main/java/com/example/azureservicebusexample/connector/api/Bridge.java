package com.example.azureservicebusexample.connector.api;

import com.example.azureservicebusexample.samples.newsample.MsgBridgeStatus;

public interface Bridge {
    void start();

    void stop();

    void restart();

    BridgeStatus status();
}
