package com.hiddensign.core.payment.db.service.trx.impl;

import com.avaje.ebean.TxType;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;
import com.hiddensign.core.payment.db.dao.trx.TrxDao;
import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.entity.trx.StatusType;
import com.hiddensign.core.payment.db.entity.trx.Trx;
import com.hiddensign.core.payment.db.entity.trx.TrxType;
import com.hiddensign.core.payment.db.service.trx.TrxService;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
@Singleton
public class TrxServiceImpl implements TrxService {

    @Inject
    private TrxDao trxDao;


    @Override
    @Transactional(type = TxType.REQUIRED)
    public Trx createOne(Account from, Account to, BigDecimal amount, TrxType type) {
        Trx trx = new Trx();
        trx.setFromAcc(from.getId());
        trx.setFromCcy(from.getCcy());
        trx.setFromAmount(amount.setScale(from.getCcy().scale(),
                RoundingMode.FLOOR));
        trx.setToAcc(to.getId());
        trx.setToCcy(to.getCcy());
        trx.setToAmount(amount.setScale(to.getCcy().scale(),
                RoundingMode.FLOOR));
        trx.setCurrencyRate(1);
        trx.setType(type);
        trx.setStatus(StatusType.DRAFT);
        trx = trxDao.createOne(trx);
        return trx;
    }

    @Override
    @Transactional(type = TxType.REQUIRED)
    public Trx postOne(long trxId) {
        Trx trx = trxDao.findOne(trxId);
        if (trx != null) {
            trx.setStatus(StatusType.POSTED);
            trx = trxDao.updateOne(trx);
        }
        return trx;
    }


}
