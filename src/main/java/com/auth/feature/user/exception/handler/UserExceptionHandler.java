package com.auth.feature.user.exception.handler;

import com.auth.core.exception.ErrorMessage;
import com.auth.core.exception.RestApiException;
import com.auth.core.exception.RestExceptionMessage;
import com.auth.feature.user.exception.EmailExistsException;
import com.auth.feature.user.exception.EmailNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * User exception handler.
 * <p>
 * Exception message is set here.
 */

@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * Email already exist exception.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailExistsException.class)
    public RestExceptionMessage emailExistsException(RestApiException e) {
        return new RestExceptionMessage(new Date(), HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT, new ErrorMessage(e.getMessage()));
    }

    /**
     * Email not found exception.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public RestExceptionMessage emailNotFoundException(RestApiException e) {
        return new RestExceptionMessage(new Date(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND, new ErrorMessage(e.getMessage()));
    }
}
