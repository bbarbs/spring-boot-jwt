package com.auth.feature.token.repository;

import com.auth.core.jwt.JwtModel;

import java.util.Date;

public interface TokenRepository {

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
    void delete(String key);

    /**
     * Get token.
     *
     * @param key
     * @return
     */
    Object get(String key);
}
