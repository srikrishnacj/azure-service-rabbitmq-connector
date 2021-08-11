package com.example.azureservicebusexample.bridge.rabbitmq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RabbitMqMsgConfig {
    private String connectionString;
    private String queue;
    private String exchange;
}
