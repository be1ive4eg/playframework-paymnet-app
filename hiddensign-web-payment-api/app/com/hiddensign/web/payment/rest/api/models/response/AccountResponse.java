package com.hiddensign.web.payment.rest.api.models.response;

import com.hiddensign.core.payment.db.entity.account.CurrencyType;
import play.data.validation.Constraints;

import java.math.BigDecimal;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public class AccountResponse {

    public Long id;

    public BigDecimal balance;

    public CurrencyType ccy;

    public AccountResponse() {
    }

    public AccountResponse(Long id, BigDecimal balance, CurrencyType ccy) {
        this.id = id;
        this.balance = balance;
        this.ccy = ccy;
    }
}
