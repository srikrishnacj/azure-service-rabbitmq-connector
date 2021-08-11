package com.example.azureservicebusexample.samples;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageGenerator {
    private String PREFIX = UUID.randomUUID().toString().substring(0, 3);
    private final AtomicInteger counter = new AtomicInteger();

    public SampleMessage generate() {
        String id = PREFIX + counter.incrementAndGet();
        String msg = UUID.randomUUID().toString();
        return new SampleMessage(id, msg);
    }
}
