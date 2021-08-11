package com.example.azureservicebusexample.bridge.servicebus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceBusMsgConfig {
    private String connectionString;
    private String queue;
}
