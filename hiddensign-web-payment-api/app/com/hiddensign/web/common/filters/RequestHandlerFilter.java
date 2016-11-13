package com.hiddensign.web.common.filters;

import akka.util.ByteString;
import com.hiddensign.web.common.GlobalErrorHandler;
import play.libs.streams.Accumulator;
import play.mvc.EssentialAction;
import play.mvc.EssentialFilter;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Executor;

/**
 * @author Nikolay Denisenko
 * @version 2016 /11/12
 */
@Singleton
public class RequestHandlerFilter extends EssentialFilter {

    private final Executor executor;
    private final GlobalErrorHandler errorHandler;

    @Inject
    public RequestHandlerFilter(Executor executor, GlobalErrorHandler errorHandler) {
        super();
        this.executor = executor;
        this.errorHandler = errorHandler;
    }

    @Override
    public EssentialAction apply(EssentialAction next) {
        return EssentialAction.of(request -> {
            Accumulator<ByteString, Result> accumulator =  next.apply(request);
            return accumulator.recoverWith(throwable ->
                    errorHandler.onServerError(request, throwable), executor);
        });
    }

}
