package com.auth.feature.token.service;

import com.auth.core.jwt.JwtModel;

import java.util.Date;

public interface TokenService {

    /**
     * Save token key.
     *
     * @param key
     * @param model
     */
    void setSecretKey(String key, JwtModel model);

    /**
     * Set key expiration.
     *
     * @param key
     * @param exp
     */
    Boolean setKeyExpiration(String key, Date exp);

    /**
     * Remove token.
     *
     * @param key
     */
    void deleteToken(String key);

    /**
     * Get token secret key.
     *
     * @param key
     * @return
     */
    Object getSecretKey(String key);
}
