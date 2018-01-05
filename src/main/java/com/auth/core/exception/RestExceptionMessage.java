package com.auth.core.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class RestExceptionMessage {

    /**
     * Format time.
     * exp: "2017-11-06 05:02 PM GMT+"
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date timestamp;
    private int statusCode;
    private HttpStatus httpStatus;
    private ErrorMessage error;

    public RestExceptionMessage(Date timestamp, int statusCode, HttpStatus httpStatus, ErrorMessage error) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RestExceptionMessage{" +
                "timestamp=" + timestamp +
                ", statusCode=" + statusCode +
                ", httpStatus=" + httpStatus +
                ", error=" + error +
                '}';
    }
}
