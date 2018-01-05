package com.auth.feature.user.exception.handler;

import com.auth.core.exception.ErrorMessage;
import com.auth.core.exception.RestApiException;
import com.auth.core.exception.RestExceptionMessage;
import com.auth.feature.user.exception.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class RoleExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public RestExceptionMessage roleNotFoundException(RestApiException e) {
        return new RestExceptionMessage(new Date(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND, new ErrorMessage(e.getMessage()));
    }
}