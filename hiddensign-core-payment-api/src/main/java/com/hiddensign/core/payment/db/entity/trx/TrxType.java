package com.hiddensign.core.payment.db.entity.trx;

import com.avaje.ebean.annotation.EnumValue;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public enum TrxType {

    @EnumValue("TRANSFER")
    TRANSFER("TRANSFER");

    private String code;

    TrxType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static TrxType forTrxCode(String code) {
        TrxType type = tryTrxCode(code);
        if (type != null) {
            return type;
        } else {
            throw new IllegalArgumentException("No matching constant for [" + type + "]");
        }
    }

    public static TrxType tryTrxCode(String code) {
        for (TrxType type : values()) {
            if (StringUtils.equalsIgnoreCase(type.code, code)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return code;
    }

}
