package com.hiddensign.core.common.data;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public interface BaseModel<T> extends Identifiable<T>, Serializable {

    // All models have these members
    String ID = "id";
    String CREATED_DATE = "createdDate";
    String UPDATED_DATE = "updatedDate";
    String VERSION = "version";

    DateTime getCreationDate();

    DateTime getLastUpdate();

    Long getVersion();

}
