package com.example.azureservicebusexample.samples.newsample.servicebus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AzureMsgConfig {
    private String friendlyName;
    private String connectionString;
    private String queueName;
}
