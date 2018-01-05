package com.auth.feature.user.exception;

import com.auth.core.exception.RestApiException;

public class UserNotFoundException extends RestApiException {

    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
