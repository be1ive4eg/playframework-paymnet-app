package com.hiddensign.web.payment.rest.api.models.requests;

import com.hiddensign.core.payment.db.entity.account.CurrencyType;
import play.data.validation.Constraints;

import java.math.BigDecimal;

/**
 * Created by Nikolay on 01-Dec-15.
 */
public class AccountCreateRequest {

    @Constraints.Required
    public BigDecimal balance;

    @Constraints.Required
    public CurrencyType ccy;

    public AccountCreateRequest() {
    }

    public AccountCreateRequest(CurrencyType ccy, BigDecimal balance) {
        this.ccy = ccy;
        this.balance = balance;
    }
}
