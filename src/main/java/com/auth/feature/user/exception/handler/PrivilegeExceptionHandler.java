package com.auth.feature.user.exception.handler;

import com.auth.core.exception.ErrorMessage;
import com.auth.core.exception.RestApiException;
import com.auth.core.exception.RestExceptionMessage;
import com.auth.feature.user.exception.PrivilegeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class PrivilegeExceptionHandler {

    @ExceptionHandler(PrivilegeNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> privilegeNotFoundException(RestApiException e) {
        return new ResponseEntity<>(new RestExceptionMessage(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                new ErrorMessage(e.getMessage())
        ), HttpStatus.NOT_FOUND);
    }
}
