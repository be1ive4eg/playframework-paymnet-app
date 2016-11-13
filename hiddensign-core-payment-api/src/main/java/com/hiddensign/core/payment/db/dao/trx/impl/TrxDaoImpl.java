package com.hiddensign.core.payment.db.dao.trx.impl;

import com.avaje.ebean.EbeanServer;
import com.hiddensign.core.payment.db.dao.trx.TrxDao;
import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.entity.trx.StatusType;
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
public class TrxDaoImpl implements TrxDao {

    private static final Logger logger = LoggerFactory.getLogger(TrxDaoImpl.class.getName());

    @Inject
    private EbeanServer ebeanServer;

    @Override
    public Trx findOne(long trxId) {
        return ebeanServer.find(Trx.class).
                where().eq("id", trxId).findUnique();
    }

    @Override
    public Trx createOne(Trx trx) {
        return save(trx);
    }

    @Override
    public Trx updateOne(Trx trx) {
        return save(trx);
    }

    private Trx save(Trx trx) {
        ebeanServer.save(trx);
        return trx;
    }
}
