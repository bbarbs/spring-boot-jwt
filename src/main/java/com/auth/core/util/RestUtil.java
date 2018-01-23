package com.auth.core.util;

import com.auth.core.rest.response.ApiResponse;
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
    public static ApiResponse createApiResponse(int statusCode, HttpStatus status, List<?> details) {
        ApiResponse res = new ApiResponse();
        res.setStatusCode(statusCode);
        res.setStatus(status);
        res.setDetails(details);
        return res;
    }
}

