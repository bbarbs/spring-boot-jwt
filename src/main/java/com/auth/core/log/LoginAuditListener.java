package com.auth.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Log every user login.
 */

@Component
public class LoginAuditListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        logger.info("Successfully authenticated: " + "user=" + auth.getName() + "; " + "date=" + new Date().getTime());
    }
}
