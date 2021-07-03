package com.example.azureservicebusexample.apiresponse.errorcodes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.azureservicebusexample.apiresponse.errordetail.ErrorDetail;

import static org.assertj.core.api.Assertions.assertThat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ValidationErrorCode implements ErrorDetail {
    BRIDGE_ID_INVALID("BRIDGE_ID_INVALID", "BRIDGE ID is empty or null or already used"),
    BRIDGE_TYPE_INVALID("BRIDGE_TYPE_INVALID", "BRIDGE TYPE is empty or null or values other then INBOUND OR OUTBOUND"),
    RMQ_CONNECTION_STRING_INVALID("RMQ_CONNECTION_STRING_INVALID", "CONNECTION STRING is null or empty or invalid"),
    RMQ_QUEUE_INVALID("RMQ_QUEUE_INVALID", "QUEUE value is empty or null or does not exists"),
    RMQ_EXCHANGE_INVALID("RMQ_EXCHANGE_INVALID", "EXCHANGE value is empty or null or does not exists"),
    SERVICE_BUS_CONNECTION_STRING_INVALID("SERVICE_BUS_CONNECTION_STRING_INVALID", "CONNECTION STRING is null or empty or invalid"),
    SERVICE_BUS_QUEUE_INVALID("SERVICE_BUS_QUEUE_INVALID", "QUEUE value is empty or null or does not exists");

    private String code, message;

    private ValidationErrorCode(String code, String message) {
        assertThat(code).isNotEmpty();
        assertThat(message).isNotEmpty();

        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
