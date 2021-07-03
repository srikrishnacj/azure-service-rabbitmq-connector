package com.example.azureservicebusexample.connector.api;

public interface Bridge {
    void start();
    void stop();
    void restart();
    BridgeStatus status();
}
