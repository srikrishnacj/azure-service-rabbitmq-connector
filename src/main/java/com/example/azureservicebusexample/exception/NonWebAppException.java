package com.example.azureservicebusexample.exception;

import com.example.azureservicebusexample.apiresponse.errordetail.ErrorDetail;

public class NonWebAppException extends RuntimeException {
    public NonWebAppException(ErrorDetail errorDetail) {
        super(errorDetail.getMessage());
    }

    public NonWebAppException(ErrorDetail errorDetail, Exception cause) {
        super(errorDetail.getMessage());
        super.initCause(cause);
    }
}
