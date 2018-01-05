package com.auth.core.exception.global;

import com.auth.core.exception.RestApiException;

public class PatchOperationNotSupported extends RestApiException {

    public PatchOperationNotSupported(String s) {
        super(s);
    }

    public PatchOperationNotSupported(String s, Throwable throwable) {
        super(s, throwable);
    }
}
