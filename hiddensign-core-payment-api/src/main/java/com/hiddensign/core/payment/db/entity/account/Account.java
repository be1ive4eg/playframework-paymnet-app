package com.hiddensign.core.payment.db.entity.account;

import com.hiddensign.core.payment.db.entity.EbeanModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
@Entity
@Table(name = Account.TABLE)
public class Account extends EbeanModel {
    public static final String TABLE = "TBL_ACCOUNT";

    @Column(name = "balance", precision = 19, scale = 4)
    private BigDecimal balance;

    @Column(name = "ccy")
    @Enumerated(EnumType.STRING)
    private CurrencyType ccy;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CurrencyType getCcy() {
        return ccy;
    }

    public void setCcy(CurrencyType ccy) {
        this.ccy = ccy;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", ccy=" + ccy +
                "} " + super.toString();
    }
}
