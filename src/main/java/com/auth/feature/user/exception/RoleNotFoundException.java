package com.auth.feature.user.exception;

import com.auth.core.exception.RestApiException;

public class RoleNotFoundException extends RestApiException {

    public RoleNotFoundException(String s) {
        super(s);
    }

    public RoleNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
