package com.hiddensign.core.common;

import com.hiddensign.core.common.utils.PropertyUtils;
import com.zaxxer.hikari.HikariConfig;

import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public class TargetProperties {
    public static void setTargetFromProperties(Object target, Properties properties, String prefix)
    {
        if (target == null || properties == null) {
            return;
        }

        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            Object key = propertyNames.nextElement();
            String propName = key.toString();
            Object propValue = properties.getProperty(propName);
            if (propValue == null) {
                propValue = properties.get(key);
            }

            if (target instanceof HikariConfig) {
                if (propName.startsWith(prefix + "dataSource.")) {
                    HikariConfig config = (HikariConfig) target;
                    config.addDataSourceProperty(propName.substring((prefix + "dataSource.").length()), propValue);
                } else if (propName.startsWith(prefix)) {
                    PropertyUtils.setProperty(target, propName.substring(prefix.length()), propValue);
                }
            }
        }
    }

}
