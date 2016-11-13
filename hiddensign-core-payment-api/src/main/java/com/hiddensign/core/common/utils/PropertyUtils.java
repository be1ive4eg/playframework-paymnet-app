package com.hiddensign.core.common.utils;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public class PropertyUtils {

    private static final String PROPERTY_FILE = "configuration.properties";
    private static final String PROPERTY_FILE_DEFAULT = "configuration.properties";

    private PropertyUtils() {}

    public static Properties loadPropertiesFromFile(String fileName) {
        //try to find file path in properties

        try (final InputStream is = loadInputStreamFromFile(fileName)) {
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                return props;
            } else {
                throw new IllegalArgumentException("Property file " + fileName + " was not found.");
            }
        } catch (IOException io) {
            throw new RuntimeException("Error loading file", io);
        }
    }

    public static Properties loadPropertiesFromFile() {
        String fileName =
                System.getProperty(PROPERTY_FILE, PROPERTY_FILE_DEFAULT);
        return PropertyUtils.loadPropertiesFromFile(fileName);
    }

    public static InputStream loadInputStreamFromFile(String fileName) {
        try {
            File file = new File(fileName);
            return file.isFile() ?
                    new FileInputStream(file) : PropertyUtils.class.getClassLoader().getResourceAsStream(fileName);
        } catch (IOException io) {
            throw new RuntimeException("Error loading file", io);
        }
    }

    public static InputStream loadInputStreamFromFile() {
        String fileName =
                System.getProperty(PROPERTY_FILE, PROPERTY_FILE_DEFAULT);
        return PropertyUtils.loadInputStreamFromFile(fileName);
    }

    public static void setProperty(Object target, String propName, Object propValue)
    {
        String capitalized = "set" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
        PropertyDescriptor propertyDescriptor;
        try {
            propertyDescriptor = new PropertyDescriptor(propName, target.getClass(), null, capitalized);
        }
        catch (IntrospectionException e) {
            capitalized = "set" + propName.toUpperCase();
            try {
                propertyDescriptor = new PropertyDescriptor(propName, target.getClass(), null, capitalized);
            }
            catch (IntrospectionException e1) {
                throw new RuntimeException("Property " + propName + " does not exist " +
                        "on target " + target.getClass(), e);
            }
        }

        try {
            Method writeMethod = propertyDescriptor.getWriteMethod();
            Class<?> paramClass = writeMethod.getParameterTypes()[0];
            if (paramClass == int.class) {
                writeMethod.invoke(target, Integer.parseInt(propValue.toString()));
            }
            else if (paramClass == long.class) {
                writeMethod.invoke(target, Long.parseLong(propValue.toString()));
            }
            else if (paramClass == boolean.class || paramClass == Boolean.class) {
                writeMethod.invoke(target, Boolean.parseBoolean(propValue.toString()));
            }
            else if (paramClass == String.class) {
                writeMethod.invoke(target, propValue.toString());
            }
            else {
                writeMethod.invoke(target, propValue);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Exception setting property " + propName + " " +
                    "on target " + target.getClass(), e);
        }
    }

    /**
     * Build a {@code Properties} object containing key-value pairs from
     * the given data where the keys are prefixed with the given
     * {@code prefix}. The keys in the returned object will be stripped
     * of their common prefix.
     *
     * @param properties Key-value data from which to extract pairs
     * @param prefix Key-value pairs where the key has this prefix will
     *               be retained in the returned {@code Properties} object
     * @return A Properties object containing those key-value pairs from
     *         {@code properties} where the key was prefixed by
     *         {@code prefix}. This prefix is removed from all keys in
     *         the returned structure.
     */
    public static Properties extractPrefixedProperties(Properties properties, String prefix) {
        return extractPrefixedProperties(properties, prefix, false);
    }

    /**
     * Build a {@code Properties} object containing key-value pairs from
     * the given data where the keys are prefixed with the given
     * {@code prefix}. The keys in the returned object will be stripped
     * of their common prefix.
     *
     * @param properties Key-value data from which to extract pairs
     * @param prefix Key-value pairs where the key has this prefix will
     *               be retained in the returned {@code Properties} object
     * @param keepPrefix whether the prefix should be kept in the key
     * @return A Properties object containing those key-value pairs from
     *         {@code properties} where the key was prefixed by
     *         {@code prefix}. If keepPrefix is false, the prefix is removed from all keys in
     *         the returned structure.
     */
    public static Properties extractPrefixedProperties(Properties properties, String prefix, boolean keepPrefix) {
        Properties ret = new Properties();

        for (String keyStr : properties.stringPropertyNames()) {
            if (keyStr.startsWith(prefix)) {
                if (!keepPrefix) {
                    String newStr = keyStr.substring(prefix.length());
                    ret.setProperty(newStr, properties.getProperty(keyStr));
                } else {
                    ret.setProperty(keyStr, properties.getProperty(keyStr));
                }
            }
        }

        return ret;
    }

    /**
     * Build a {@code Properties} object containing key-value pairs from
     * the given properties whose keys are in a list to keep.
     *
     * @param properties Key-value data from which to extract pairs
     * @param keptProperties Key names to keep (by exact match).
     * @return A Properties object containing those key-value pairs from
     *         {@code properties} where the key was in keptProperties
     */
    public static Properties extractSelectedProperties(Properties properties, Set<String> keptProperties) {
        Properties ret = new Properties();

        for (String keyStr : properties.stringPropertyNames()) {
            if (keptProperties.contains(keyStr)) {
                ret.setProperty(keyStr, properties.getProperty(keyStr));
            }
        }

        return ret;
    }



}
