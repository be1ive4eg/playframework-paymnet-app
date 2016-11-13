package com.hiddensign.core.payment.db.dao.account;

import com.google.inject.ImplementedBy;
import com.hiddensign.core.payment.db.dao.account.impl.AccountDaoImpl;
import com.hiddensign.core.payment.db.dao.trx.impl.TrxDaoImpl;
import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.entity.trx.Trx;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
@ImplementedBy(AccountDaoImpl.class)
public interface AccountDao {

    Account findOne(long accountId);

    Account lockOne(long accountId);

    Account createOne(Account account);

    Account updateOne(Account account);

}
