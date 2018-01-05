package com.auth.feature.token.exception;

import com.auth.core.exception.RestApiException;

public class TokenNotFoundException extends RestApiException {

    public TokenNotFoundException(String s) {
        super(s);
    }

    public TokenNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
