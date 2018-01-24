package com.auth.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * Listener to log every http request.
 */

@Component
public class HttpAuditListener implements ApplicationListener<ServletRequestHandledEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        logger.info("Request details: " + "user=" + event.getUserName() + "; " +
                "url=" + event.getRequestUrl() + "; " +
                "method=" + event.getMethod() + "; " +
                "processing-time=" + event.getProcessingTimeMillis() + "; " +
                "status=" + event.getStatusCode() + "; " +
                "date=" + event.getTimestamp() + ";");
    }
}
