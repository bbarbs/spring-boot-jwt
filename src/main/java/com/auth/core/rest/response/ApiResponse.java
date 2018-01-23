package com.auth.core.rest.response;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ApiResponse {

    @NotNull
    private int statusCode;
    @NotNull
    private HttpStatus status;
    private List<?> details;

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

    public List<?> getDetails() {
        return details;
    }

    public void setDetails(List<?> details) {
        this.details = details;
    }
}
