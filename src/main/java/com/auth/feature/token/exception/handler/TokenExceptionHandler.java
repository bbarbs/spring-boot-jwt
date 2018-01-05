package com.auth.feature.token.exception.handler;

import com.auth.core.exception.ErrorMessage;
import com.auth.core.exception.RestApiException;
import com.auth.core.exception.RestExceptionMessage;
import com.auth.feature.token.exception.TokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Token exception handler.
 * <p>
 * Exception message is set here when exception occur.
 */

@RestControllerAdvice
public class TokenExceptionHandler {

    /**
     * Token not found exception.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(TokenNotFoundException.class)
    public RestExceptionMessage tokenNotFoundException(RestApiException e) {
        return new RestExceptionMessage(new Date(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND, new ErrorMessage(e.getMessage()));
    }
}
