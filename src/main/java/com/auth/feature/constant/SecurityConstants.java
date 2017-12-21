package com.auth.feature.constant;

public interface SecurityConstants {
    String SECRET_KEY = "SecretKey";
    long EXPIRATION_TIME = 864_000_000; // 10 days
    String TOKEN_PREFIX = "Bearer ";
    String AUTHORIZATION_HEADER_STRING = "Authorization";
}
