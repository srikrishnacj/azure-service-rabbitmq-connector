package com.example.azureservicebusexample.samples.rabbitmq;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.time.Duration;

public class RabbitMqCommonMain {
    protected void configMicroMeter() {
        CompositeMeterRegistry globalRegistry = Metrics.globalRegistry;
        SimpleMeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
        globalRegistry.add(simpleMeterRegistry);


        LoggingMeterRegistry registry = new LoggingMeterRegistry(new LoggingRegistryConfig() {
            @Override
            public Duration step() {
                return Duration.ofSeconds(1); // log every 10 seconds
            }

            @Override
            public String get(String key) {
                return null;
            }
        }, Clock.SYSTEM);
        globalRegistry.add(registry);
    }
}
