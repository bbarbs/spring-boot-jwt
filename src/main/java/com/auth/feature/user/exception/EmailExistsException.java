package com.auth.feature.user.exception;

import com.auth.core.exception.RestApiException;

public class EmailExistsException extends RestApiException {

    public EmailExistsException(String s) {
        super(s);
    }

    public EmailExistsException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
