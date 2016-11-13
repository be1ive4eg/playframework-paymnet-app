package com.hiddensign.core.common;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.hiddensign.core.common.utils.PropertyUtils;

import java.util.Properties;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public class GuicePropertyConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        Properties props = PropertyUtils.loadPropertiesFromFile();

        //load properties
        Names.bindProperties(binder(), props);
    }

}
