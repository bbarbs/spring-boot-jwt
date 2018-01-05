package com.auth.core.exception.handler;

import com.auth.core.exception.ErrorMessage;
import com.auth.core.exception.RestApiException;
import com.auth.core.exception.RestExceptionMessage;
import com.auth.core.exception.global.PatchOperationNotSupported;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Global exceptions handler.
 * <p>
 * Exception message is set here instead of passing it through constructor.
 */

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    /**
     * Patch operation not supported exception.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(PatchOperationNotSupported.class)
    public RestExceptionMessage patchOperationNotSupported(RestApiException e) {
        return new RestExceptionMessage(new Date(), HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE, new ErrorMessage(e.getMessage()));
    }
}
