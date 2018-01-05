package com.auth.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Base class exception for rest api endpoints.
 */

public class RestApiException extends AuthenticationException {

    public RestApiException(String s) {
        super(s);
    }

    public RestApiException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
