package com.hiddensign.core.payment.db.dao.account.impl;

import com.avaje.ebean.EbeanServer;
import com.hiddensign.core.payment.db.dao.account.AccountDao;
import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.entity.trx.Trx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
@Singleton
public class AccountDaoImpl implements AccountDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class.getName());

    @Inject
    private EbeanServer ebeanServer;

    @Override
    public Account findOne(long accountId) {
        return ebeanServer.find(Account.class).
                where().eq("id", accountId).findUnique();
    }

    @Override
    public Account lockOne(long accountId) {
        return ebeanServer.find(Account.class).setForUpdate(true).setTimeout(5).
                where().eq("id", accountId).findUnique();
    }

    @Override
    public Account createOne(Account account) {
        return save(account);
    }

    @Override
    public Account updateOne(Account account) {
        return save(account);
    }

    private Account save(Account account) {
        ebeanServer.save(account);
        return account;
    }
}
