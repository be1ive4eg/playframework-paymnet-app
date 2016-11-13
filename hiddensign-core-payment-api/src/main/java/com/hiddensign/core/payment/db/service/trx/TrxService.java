package com.hiddensign.core.payment.db.service.trx;

import com.google.inject.ImplementedBy;
import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.entity.trx.Trx;
import com.hiddensign.core.payment.db.entity.trx.TrxType;
import com.hiddensign.core.payment.db.service.trx.impl.TrxServiceImpl;

import java.math.BigDecimal;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
@ImplementedBy(TrxServiceImpl.class)
public interface TrxService {

    Trx createOne(Account from, Account to, BigDecimal amount, TrxType type);

    Trx postOne(long trxId);
}
