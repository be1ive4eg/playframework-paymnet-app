package com.hiddensign.web.payment.rest.api.models.requests;

import play.data.validation.Constraints;

import java.math.BigDecimal;

/**
 * Created by Nikolay on 01-Dec-15.
 */
public class AccountTransferRequest {

    @Constraints.Required
    public long fromAcc;

    @Constraints.Required
    public long toAcc;

    @Constraints.Required
    public BigDecimal amount;

    public AccountTransferRequest() {
    }

    public AccountTransferRequest(long fromAcc, long toAcc, BigDecimal amount) {
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
        this.amount = amount;
    }
}
