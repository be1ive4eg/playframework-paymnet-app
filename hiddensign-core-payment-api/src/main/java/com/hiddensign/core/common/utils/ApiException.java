package com.hiddensign.core.common.utils;

/**
 * @author Nikolay Denisenko
 * @version 2015/08/08
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
