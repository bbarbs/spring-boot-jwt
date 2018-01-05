package com.auth.feature.user.exception;

import com.auth.core.exception.RestApiException;

public class PrivilegeNotFoundException extends RestApiException {

    public PrivilegeNotFoundException(String s) {
        super(s);
    }

    public PrivilegeNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
