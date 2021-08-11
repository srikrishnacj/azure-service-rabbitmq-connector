package com.example.azureservicebusexample.apiresponse.errorcodes;

import com.example.azureservicebusexample.apiresponse.errordetail.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonFormat;

import static org.assertj.core.api.Assertions.assertThat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonResourceErrorCode implements ErrorDetail {
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "resource not found"),
    INVALID_FORM("INVALID_FORM", "one or more form fields does not pass validation");
    
    private String code, message;

    private CommonResourceErrorCode(String code, String message) {
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
