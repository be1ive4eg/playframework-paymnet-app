package com.hiddensign.core.payment.db.service.account;

import com.google.inject.ImplementedBy;
import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.entity.account.CurrencyType;
import com.hiddensign.core.payment.db.service.account.impl.AccountServiceImpl;

import java.math.BigDecimal;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
@ImplementedBy(AccountServiceImpl.class)
public interface AccountService {

    Account findOne(long accId);

    Account createOne(BigDecimal balance, CurrencyType ccy);

    void transfer(long fromAcc, long toAcc, BigDecimal amount);

}
