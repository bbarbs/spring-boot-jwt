package com.auth.core.handler;

import com.auth.core.jwt.util.JwtUtil;
import com.auth.feature.token.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HttpLogoutHandler implements LogoutSuccessHandler {

    @Inject
    TokenService tokenService;

    @Inject
    JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        // Remove token in redis.
        String token = this.jwtUtil.extractToken(request);
        if (token != null) {
            this.tokenService.deleteToken(token);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().flush();
    }
}
