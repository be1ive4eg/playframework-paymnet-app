package com.hiddensign.core.common.utils;

/**
 * @author Nikolay Denisenko
 * @version 2015/08/08
 */
public class TransferException extends ApiException {

    public TransferException(String message) {
        super(message);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }
}
