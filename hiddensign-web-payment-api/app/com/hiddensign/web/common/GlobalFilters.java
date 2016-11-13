package com.hiddensign.web.common;

import com.hiddensign.web.common.filters.RequestHandlerFilter;
import com.hiddensign.web.common.filters.RequestLoggerFilter;
import play.http.HttpFilters;
import play.mvc.EssentialFilter;

import javax.inject.Inject;

/**
 * @author Nikolay Denisenko
 * @version 2016 /11/12
 */
public class GlobalFilters implements HttpFilters {


    @Inject
    private RequestLoggerFilter rlf;

    @Inject
    private RequestHandlerFilter rhf;

    @Override
    public EssentialFilter[] filters() {
        return new EssentialFilter[] {
                rlf.asJava()
                ,rhf.asJava()
        };
    }
}
