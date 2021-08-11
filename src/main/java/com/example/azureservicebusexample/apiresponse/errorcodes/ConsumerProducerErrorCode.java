package com.example.azureservicebusexample.apiresponse.errorcodes;

import com.example.azureservicebusexample.apiresponse.errordetail.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonFormat;

import static org.assertj.core.api.Assertions.assertThat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConsumerProducerErrorCode implements ErrorDetail {

    MESSAGE_FAILED("MESSAGE_FAILED", "Unable to send message"),
    PRODUCER_BOOT_FAILED("PRODUCER_BOOT_FAILED", "Unable to start producer"),
    CONSUMER_BOOT_FAILED("CONSUMER_BOOT_FAILED", "Unable to start consumer");

    private String code, message;

    private ConsumerProducerErrorCode(String code, String message) {
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
