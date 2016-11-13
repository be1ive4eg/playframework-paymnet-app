package com.hiddensign.web.payment.rest.api;

import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public class GlobalGuiceModule extends AbstractModule implements AkkaGuiceSupport {

    protected void configure() {
        //configure global guice modules
    }


}
