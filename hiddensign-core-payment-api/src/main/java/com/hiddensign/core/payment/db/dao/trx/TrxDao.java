package com.hiddensign.core.payment.db.dao.trx;

import com.google.inject.ImplementedBy;
import com.hiddensign.core.payment.db.dao.trx.impl.TrxDaoImpl;
import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.entity.trx.Trx;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
@ImplementedBy(TrxDaoImpl.class)
public interface TrxDao {

    Trx findOne(long trxId);

    Trx createOne(Trx trx);

    Trx updateOne(Trx trx);

}
