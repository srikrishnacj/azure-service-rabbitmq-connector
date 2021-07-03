package com.example.azureservicebusexample.apiresponse.restresponse;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public abstract class RestResponse {
    private long timestamp = System.currentTimeMillis();
    private HttpStatus status;

    public abstract ResponseEntity responseEntity();
}