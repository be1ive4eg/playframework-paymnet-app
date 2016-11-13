package com.hiddensign.web.common;

import com.hiddensign.core.common.utils.TransferException;
import com.hiddensign.web.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.http.HttpErrorHandler;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

/**
 * @author Nikolay Denisenko
 * @version 2016 /11/12
 */
public class GlobalErrorHandler implements HttpErrorHandler {

    @Override
    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        Logger.error("Just on server error of {} {}: {}.", request.method(), request.uri(), exception.getMessage());
        if (exception instanceof CompletionException) {
            exception = exception.getCause();
        }
        if (exception instanceof TransferException) {
            return CompletableFuture.completedFuture(Results.badRequest(
                    (JsonUtils.response("Transfer failured because of: " + exception.getMessage()))));
        } else {
            Logger.error("Not handled exception in {} {}: {}.", request.method(), request.uri(), exception);
            return CompletableFuture.completedFuture(Results.internalServerError(
                    (JsonUtils.response("Our server doesn't like your request, try again later."))));
        }
    }

    @Override
    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        Logger.info("Just on bad request.");
        return CompletableFuture.completedFuture(Results.badRequest
                (JsonUtils.response(StringUtils.defaultIfBlank(message,
                        "Bad move, don't try again in this way."))));
    }

}
