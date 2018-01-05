package com.auth.core.jwt.util;

public interface JwtConstant {
    String AUTHORIZATION_HEADER_STRING = "Authorization";
    String TOKEN_BEARER_PREFIX = "Bearer ";
    Integer ACCESS_TOKEN_EXPIRATION = 60; // minutes
}
