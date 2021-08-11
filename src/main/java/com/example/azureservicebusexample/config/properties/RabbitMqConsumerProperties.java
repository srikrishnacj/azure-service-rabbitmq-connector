package com.example.azureservicebusexample.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq.config")
@Data
public class RabbitMqConsumerProperties {
    private int prefetch = 10;
}
