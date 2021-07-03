package com.example.azureservicebusexample.exception;

import lombok.Getter;
import com.example.azureservicebusexample.apiresponse.restresponse.ErrorRestResponse;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class AppException extends RuntimeException {
    private String message;
    private ErrorRestResponse restResponse;

    public AppException(ErrorRestResponse restResponse) {
        assertThat(restResponse).isNotNull();
        this.message = restResponse.getError().getMessage();
        this.restResponse = restResponse;
        this.restResponse.setStackTrace(Arrays.stream(this.getStackTrace()).map(trace -> trace.toString()).toArray());
    }

    public ResponseEntity responseEntity() {
        return this.restResponse.responseEntity();
    }
}
