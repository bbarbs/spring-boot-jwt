package com.auth.core.security;

import com.auth.core.config.WebSecurityConfig;
import com.auth.core.jwt.JwtHelper;
import com.auth.core.jwt.JwtModel;
import com.auth.core.jwt.util.JwtConstant;
import com.auth.core.jwt.util.JwtUtil;
import com.auth.feature.token.service.TokenService;
import com.auth.feature.user.model.dto.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Customize authentication filter so we don't need to add intercept url for login in http security.
 * We can implement this by adding it in {@link WebSecurityConfig} http security filter.
 */

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationManager authManager;
    private TokenService tokenService;
    private JwtHelper jwtHelper;
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authManager, TokenService tokenService,
                                   JwtHelper jwtHelper, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.jwtHelper = jwtHelper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Map dto value.
            AuthRequest req = getCredentials(request);
            // Authenticate user.
            return this.authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    req.getEmail(),
                    req.getPassword()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) {
        try {
            SecurityContextHolder.getContext().setAuthentication(auth);
            // Generate secret key.
            SecretKey secretKey = this.jwtUtil.generateKey();
            // Create token.
            JwtModel model = this.jwtHelper.generateAccessToken(((User) auth.getPrincipal()).getUsername(), secretKey);
            // Set token.
            this.tokenService.setSecretKey(model.getToken(), model);
            // Set key expiration on redis.
            this.tokenService.setKeyExpiration(model.getToken(), model.getExpDate());
            // Add token to authorization header.
            response.addHeader(JwtConstant.AUTHORIZATION_HEADER_STRING, JwtConstant.TOKEN_BEARER_PREFIX + model.getToken());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AuthRequest getCredentials(HttpServletRequest request) {
        // Map dto value.
        AuthRequest auth = null;
        try {
            auth = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return auth;
    }
}
