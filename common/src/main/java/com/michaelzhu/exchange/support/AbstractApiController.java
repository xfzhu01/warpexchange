package com.michaelzhu.exchange.support;

import com.michaelzhu.exchange.ApiError;
import com.michaelzhu.exchange.ApiErrorResponse;
import com.michaelzhu.exchange.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractApiController extends LoggerSupport {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ApiErrorResponse handleException(HttpServletResponse response, Exception exception) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        ApiException apiException = null;
        if (exception instanceof ApiException) {
            apiException = (ApiException) exception;
        } else {
            apiException = new ApiException(ApiError.INTERNAL_SERVER_ERROR, null, exception.getMessage());
        }
        return apiException.error;
    }
}
