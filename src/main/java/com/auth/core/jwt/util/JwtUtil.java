package com.auth.core.jwt.util;

import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class JwtUtil {

    /**
     * Get the token from authorization header.
     *
     * @param request
     * @return token
     */
    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(JwtConstant.AUTHORIZATION_HEADER_STRING);
        if (authHeader != null || authHeader.startsWith(JwtConstant.TOKEN_BEARER_PREFIX)) {
            return authHeader.replace(JwtConstant.TOKEN_BEARER_PREFIX, "");
        }
        return null;
    }

    /**
     * Generate new secret key.
     *
     * @return key
     */
    public SecretKey generateKey() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance("AES").generateKey();
    }

    /**
     * Retain generated key.
     *
     * @param decodedKey
     * @return key
     */
    public SecretKey retainKey(byte[] decodedKey) {
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    /**
     * Encode key to string.
     *
     * @param secretKey
     * @return key
     */
    public String encodeKeyToString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Decode key to byte.
     *
     * @param key
     * @return
     */
    public byte[] decodeKeyToByte(String key) {
        return Base64.getDecoder().decode(key);
    }
}
