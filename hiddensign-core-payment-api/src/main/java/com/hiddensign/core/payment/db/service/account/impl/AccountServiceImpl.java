package com.hiddensign.core.payment.db.service.account.impl;

import com.avaje.ebean.TxType;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;
import com.hiddensign.core.common.utils.ApiException;
import com.hiddensign.core.common.utils.TransferException;
import com.hiddensign.core.payment.db.dao.account.AccountDao;
import com.hiddensign.core.payment.db.entity.account.Account;
import com.hiddensign.core.payment.db.entity.account.CurrencyType;
import com.hiddensign.core.payment.db.entity.trx.Trx;
import com.hiddensign.core.payment.db.entity.trx.TrxType;
import com.hiddensign.core.payment.db.service.account.AccountService;
import com.hiddensign.core.payment.db.service.trx.TrxService;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
@Singleton
public class AccountServiceImpl implements AccountService {

    @Inject
    private AccountDao accountDao;

    @Inject
    private TrxService trxService;

    @Override
    public Account findOne(long accId) {
        return accountDao.findOne(accId);
    }

    @Override
    @Transactional(type = TxType.REQUIRED)
    public Account createOne(BigDecimal balance, CurrencyType ccy) {
        Account account = new Account();
        account.setBalance(balance.setScale(ccy.scale(),
                RoundingMode.FLOOR));
        account.setCcy(ccy);
        account = accountDao.createOne(account);
        return account;
    }

    @Override
    @Transactional(type = TxType.REQUIRED)
    public void transfer(long fromAcc, long toAcc, BigDecimal amount) {
        Account from = accountDao.findOne(fromAcc);
        Account to = accountDao.findOne(fromAcc);
        if (from != null &&
                to != null)
        {
            if (from.getCcy() == to.getCcy()) {
                if (BigDecimal.ZERO.compareTo(from.getBalance().subtract(amount)) < 0) {
                    //lock with wait
                    try {
                        if (fromAcc > toAcc) {
                            from = accountDao.lockOne(fromAcc);
                            to = accountDao.lockOne(toAcc);
                        } else {
                            to = accountDao.lockOne(toAcc);
                            from = accountDao.lockOne(fromAcc);
                        }

                        //when both accounts locked create transaction
                        Trx trx = trxService.createOne(from, to, amount, TrxType.TRANSFER);
                        //update balances
                        updateBalance(from, trx.getFromAmount().
                                multiply(BigDecimal.valueOf(-1)));
                        updateBalance(to, trx.getToAmount());
                        //post transaction
                        trxService.postOne(trx.getId());
                    } catch (Exception e) {
                        //will unlock rows on rollback automatically
                        throw new TransferException("Can't transfer due to exception", e);
                    }
                } else {
                    throw new TransferException("Account balance hasn't enough money.");
                }
            } else {
                throw new TransferException("Operations between different currencies not yet implemented.");
            }
        } else {
            throw new TransferException("One or more account doesn't exist.");
        }
    }

    @Transactional(type = TxType.REQUIRED)
    private Account updateBalance(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount).
                setScale(account.getCcy().scale(), RoundingMode.FLOOR));
        account = accountDao.updateOne(account);
        return account;
    }
}
