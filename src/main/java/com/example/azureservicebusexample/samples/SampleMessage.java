package com.example.azureservicebusexample.samples;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.charset.StandardCharsets;

@Data
@AllArgsConstructor
public class SampleMessage {
    private String id;
    private String msg;

    public byte[] data(){
        return msg.getBytes(StandardCharsets.UTF_8);
    }
}
