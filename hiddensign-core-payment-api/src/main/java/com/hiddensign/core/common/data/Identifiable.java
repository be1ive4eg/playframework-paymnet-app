package com.hiddensign.core.common.data;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public interface Identifiable<T> {

    T getId();

    void setId(T id);
}
