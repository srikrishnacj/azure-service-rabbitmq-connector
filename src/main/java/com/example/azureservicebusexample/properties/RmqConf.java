package com.example.azureservicebusexample.properties;

import lombok.Data;

@Data
public class RmqConf {
    private String connectionString;
    private String exchange;
    private String queue;
}
