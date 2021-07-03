package com.example.azureservicebusexample.properties;

import lombok.Data;

@Data
public class ServiceBusConf {
    private String connectionString;
    private String queue;
}
