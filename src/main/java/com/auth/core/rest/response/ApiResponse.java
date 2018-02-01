package com.auth.core.rest.response;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ApiResponse<T> {

    @NotNull
    private int statusCode;
    @NotNull
    private HttpStatus status;
    private List<T> details;

    public ApiResponse(int statusCode, HttpStatus status, List<T> details) {
        this.statusCode = statusCode;
        this.status = status;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<T> getDetails() {
        return details;
    }

    public void setDetails(List<T> details) {
        this.details = details;
    }
}
