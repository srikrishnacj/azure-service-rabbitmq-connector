package com.example.azureservicebusexample.apiresponse.restresponse;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@Getter
@Setter
public class OkRestResponse extends RestResponse {
    private Object data;
    private boolean isCollection; // if -1 then data is object or null. if 0 or > 0 then data is collection

    public static OkRestResponse ok(Object data) {
        return status(HttpStatus.OK).data(data);
    }

    public static OkRestResponse created(Object data) {
        return status(HttpStatus.CREATED).data(data);
    }

    public static OkRestResponse accepted(Object data) {
        return status(HttpStatus.ACCEPTED).data(data);
    }

    private static OkRestResponse status(HttpStatus status) {
        OkRestResponse restResponse = new OkRestResponse();
        restResponse.setStatus(status);
        return restResponse;
    }

    public OkRestResponse data(Object data) {
        this.data = data;
        if (data instanceof Collection) {
            this.isCollection = true;
        }
        return this;
    }

    public ResponseEntity responseEntity() {
        ResponseEntity responseEntity;
        if (data == null) {
            responseEntity = new ResponseEntity(this.getStatus());
        } else {
            responseEntity = new ResponseEntity(this, this.getStatus());
        }
        return responseEntity;
    }
}
