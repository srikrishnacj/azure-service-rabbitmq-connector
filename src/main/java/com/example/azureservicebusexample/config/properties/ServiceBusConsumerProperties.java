package com.example.azureservicebusexample.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service.config")
@Data
public class ServiceBusConsumerProperties {
    private int maxRetries = 1;
    private int retryDelayInSecs = 3;
    private int maxConcurrentCalls = 1;
    private int prefetchCount = 10;
}
