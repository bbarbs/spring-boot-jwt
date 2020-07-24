package com.auth.core.log;

import com.auth.core.jwt.JwtModel;
import com.auth.core.jwt.util.JwtUtil;
import com.auth.feature.token.service.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Spring AOP logging.
 */

@Component
@Aspect
public class AspectLogging {

    private final JwtUtil jwtUtil;

    private final TokenService tokenService;

    @Autowired
    public AspectLogging(JwtUtil jwtUtil, TokenService tokenService) {
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    /**
     * Log when user successfully authenticated.
     * @param joinPoint
     * @param result
     * @throws Throwable
     *
     @AfterReturning( pointcut = "execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))",
     returning = "result"
     )
     public void logSuccessfulAuthentication(JoinPoint joinPoint, Object result) {
     Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
     logger.info("Successfully authenticated: " + ((Authentication) result).getName() + ", " + "date created: " + new Date().getTime());
     }*/

    /**
     * Log when user successfully logout.
     *
     * @param joinPoint
     */
    @Before(
            "execution(* org.springframework.security.web.authentication.logout.LogoutSuccessHandler.onLogoutSuccess(..))"
    )
    public void logUserLogout(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
        JwtModel model = null;
        try {
            HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
            model = (JwtModel) this.tokenService.getSecretKey(this.jwtUtil.extractToken(request));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            String username = model == null ? "no user" : model.getSubject();
            logger.info("Logging out: " + "user=" + username + "; " + "date=" + new Date().getTime());
        }
    }
}
