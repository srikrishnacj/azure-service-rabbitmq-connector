package com.example.azureservicebusexample.apiresponse.restresponse;

import com.example.azureservicebusexample.apiresponse.errordetail.ErrorDetail;
import com.example.azureservicebusexample.apiresponse.errordetail.FormErrorDetail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Getter
@Setter
public class ErrorRestResponse extends RestResponse {
    private ErrorDetail error;
    private List<ErrorDetail> subErrors = new LinkedList<>();
    private Object cause[];
    private Object stackTrace[];

    public static ErrorRestResponse badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    public static ErrorRestResponse unauthorized() {
        return status(HttpStatus.UNAUTHORIZED);
    }

    public static ErrorRestResponse forbidden() {
        return status(HttpStatus.FORBIDDEN);
    }

    public static ErrorRestResponse notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    public static ErrorRestResponse conflict() {
        return status(HttpStatus.CONFLICT);
    }

    public static ErrorRestResponse validationError() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ErrorRestResponse unknown() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ErrorRestResponse notImplemented() {
        return status(HttpStatus.NOT_IMPLEMENTED);
    }

    public static ErrorRestResponse upstreamError() {
        return status(HttpStatus.BAD_GATEWAY);
    }

    private static ErrorRestResponse status(HttpStatus status) {
        ErrorRestResponse restResponse = new ErrorRestResponse();
        restResponse.setStatus(status);
        return restResponse;
    }

    public ErrorRestResponse error(ErrorDetail error) {
        assertThat(error).isNotNull();
        this.error = error;
        return this;
    }

    public ErrorRestResponse subError(ErrorDetail errorDetail) {
        assertThat(errorDetail).isNotNull();
        subErrors.add(errorDetail);
        return this;
    }

    public ErrorRestResponse fieldError(String field, ErrorDetail error) {
        assertThat(field).isNotEmpty();
        assertThat(error).isNotNull();

        this.subError(new FormErrorDetail(field, error));
        return this;
    }

    public ErrorRestResponse cause(Exception exception) {
        assertThat(exception).isNotNull();
        this.cause = Arrays.stream(exception.getStackTrace()).map(trace -> trace.toString()).toArray();
        return this;
    }

    public boolean containsErrors() {
        return error != null || subErrors.size() > 0;
    }

    public void disableStackTraceAndCause() {
        this.stackTrace = null;
        this.cause = null;
    }

    @Override
    public ResponseEntity responseEntity() {
        return new ResponseEntity(this, super.getStatus());
    }
}
