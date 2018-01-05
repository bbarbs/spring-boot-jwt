package com.auth.core.security;

import com.auth.core.jwt.JwtHelper;
import com.auth.core.jwt.JwtModel;
import com.auth.core.jwt.util.JwtConstant;
import com.auth.core.jwt.util.JwtUtil;
import com.auth.feature.token.service.TokenService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Authorization filter for every dto.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserDetailsService userDetailsService;
    private TokenService tokenService;
    private JwtHelper jwtHelper;
    private JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                                  TokenService tokenService, JwtHelper jwtHelper, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.jwtHelper = jwtHelper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) {
        try {
            // Check for authorization header existence.
            String header = request.getHeader(JwtConstant.AUTHORIZATION_HEADER_STRING);
            if (header == null || !header.startsWith(JwtConstant.TOKEN_BEARER_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            // Validate request..
            UsernamePasswordAuthenticationToken authorization = authorizeRequest(request);
            SecurityContextHolder.getContext().setAuthentication(authorization);
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validate token.
     *
     * @param request
     * @return authentication
     */
    private UsernamePasswordAuthenticationToken authorizeRequest(HttpServletRequest request) {
        try {
            // Get token.
            String token = this.jwtUtil.extractToken(request);
            if (token != null) {
                // Get token key.
                JwtModel model = (JwtModel) this.tokenService.getSecretKey(token);
                // Validate token.
                Claims claims = this.jwtHelper.validateToken(model.getSecretKey(), model);
                // Validate user authority/role if allowed to do the api dto.
                String user = claims.getSubject();
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(user);
                if (userDetails != null) {
                    return new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
