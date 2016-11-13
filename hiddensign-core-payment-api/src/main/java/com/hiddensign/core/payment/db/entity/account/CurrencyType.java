package com.hiddensign.core.payment.db.entity.account;

import com.avaje.ebean.annotation.EnumValue;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public enum CurrencyType {

    @EnumValue("RUB")
    RUB("RUB", 2),

    @EnumValue("USD")
    USD("USD", 3);

    private String code;

    private int scale;

    CurrencyType(String code, int scale) {
        this.code = code;
        this.scale = scale;
    }

    public String code() {
        return code;
    }

    public int scale() {
        return scale;
    }

    public static CurrencyType forCurrencyCode(String code) {
        CurrencyType currency = tryCurrencyCode(code);
        if (currency != null) {
            return currency;
        } else {
            throw new IllegalArgumentException("No matching constant for [" + currency + "]");
        }
    }

    public static CurrencyType tryCurrencyCode(String code) {
        for (CurrencyType currency : values()) {
            if (StringUtils.equalsIgnoreCase(currency.code, code)) {
                return currency;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return code;
    }

}
