package com.hiddensign.core.payment.db.entity.trx;

import com.avaje.ebean.annotation.EnumValue;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public enum StatusType {

    @EnumValue("DRAFT")
    DRAFT("DRAFT"),

    @EnumValue("POSTED")
    POSTED("POSTED");

    private String code;

    StatusType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static StatusType forStatusCode(String code) {
        StatusType status = tryStatusCode(code);
        if (status != null) {
            return status;
        } else {
            throw new IllegalArgumentException("No matching constant for [" + status + "]");
        }
    }

    public static StatusType tryStatusCode(String code) {
        for (StatusType status : values()) {
            if (StringUtils.equalsIgnoreCase(status.code, code)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return code;
    }

}
