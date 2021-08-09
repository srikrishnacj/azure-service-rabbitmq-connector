package com.example.azureservicebusexample.samples.newsample.rabbitmq.msgproducer;

import com.azure.core.annotation.Get;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RabbitMqMsgProducerConfig {
    private String friendlyName;
    private String connectionString;
    private String exchange;
}
