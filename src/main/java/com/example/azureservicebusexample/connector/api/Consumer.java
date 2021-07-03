package com.example.azureservicebusexample.connector.api;

public interface Consumer {
    String getName();
    void validateConf();
    void setListener(ConsumerListener listener);
    void start();
    void stop();
}
