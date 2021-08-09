package com.example.azureservicebusexample.samples.newsample.rabbitmq.msgconsumer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RabbitMqMsgConsumerConfig {
    private String connectionString;
    private String queue;
}
