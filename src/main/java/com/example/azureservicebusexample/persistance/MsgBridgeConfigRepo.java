package com.example.azureservicebusexample.persistance;

import com.example.azureservicebusexample.bridge.MsgBridgeConfig;

import java.util.List;

public interface MsgBridgeConfigRepo {
    public List<MsgBridgeConfig> loadConfig();
}
