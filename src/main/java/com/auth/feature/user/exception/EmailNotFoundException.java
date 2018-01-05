package com.auth.feature.user.exception;

import com.auth.core.exception.RestApiException;

public class EmailNotFoundException extends RestApiException {

    public EmailNotFoundException(String s) {
        super(s);
    }

    public EmailNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
