package com.example.azureservicebusexample.persistance;

import com.example.azureservicebusexample.bridge.MsgBridgeConfig;
import com.example.azureservicebusexample.utils.JsonUtil;
import com.google.common.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class JsonFileBasedMsgBridgeConfigRepo implements MsgBridgeConfigRepo {
    @Override
    public List<MsgBridgeConfig> loadConfig() {
        Type REVIEW_TYPE = new TypeToken<ArrayList<MsgBridgeConfig>>() {
        }.getType();

        try {
            List<MsgBridgeConfig> bridgeConfList = (List<MsgBridgeConfig>) JsonUtil.loadFromClasspath("bridge.json", REVIEW_TYPE);
            return bridgeConfList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
