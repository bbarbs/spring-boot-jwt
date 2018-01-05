package com.auth.core.util;

import com.auth.core.rest.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestUtil {

    /**
     * Create response message.
     *
     * @param statusCode
     * @param status
     * @param details
     * @return
     */
    public static ResponseMessage createApiResponse(int statusCode, HttpStatus status, List<?> details) {
        ResponseMessage res = new ResponseMessage();
        res.setStatusCode(statusCode);
        res.setStatus(status);
        res.setDetails(details);
        return res;
    }
}

