package com.hiddensign.core.payment.db.entity.trx;

import com.hiddensign.core.payment.db.entity.EbeanModel;
import com.hiddensign.core.payment.db.entity.account.CurrencyType;

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
@Table(name = TrxEntry.TABLE)
public class TrxEntry extends EbeanModel {

    public static final String TABLE = "TBL_TRX_ENTRY";

    @Column(name = "trx_id")
    private long trxId;

    @Column(name = "acc")
    private long acc;

    @Column(name = "ccy")
    @Enumerated(EnumType.STRING)
    private CurrencyType ccy;

    @Column(name = "debit", precision = 19, scale = 4)
    private BigDecimal debit;

    @Column(name = "credit", precision = 19, scale = 4)
    private BigDecimal credit;

    public long getTrxId() {
        return trxId;
    }

    public void setTrxId(long trxId) {
        this.trxId = trxId;
    }

    public long getAcc() {
        return acc;
    }

    public void setAcc(long acc) {
        this.acc = acc;
    }

    public CurrencyType getCcy() {
        return ccy;
    }

    public void setCcy(CurrencyType ccy) {
        this.ccy = ccy;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "TrxEntry{" +
                "trxId=" + trxId +
                ", acc=" + acc +
                ", ccy=" + ccy +
                ", debit=" + debit +
                ", credit=" + credit +
                "} " + super.toString();
    }
}
