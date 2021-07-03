package com.example.azureservicebusexample.apiresponse.errordetail;

import lombok.Getter;

@Getter
public class FormErrorDetail implements ErrorDetail {
    private String field, code, message;

    public FormErrorDetail(String field, ErrorDetail errorDetail) {
        this.field = field;
        this.code = errorDetail.getCode();
        this.message = errorDetail.getMessage();
    }
}
