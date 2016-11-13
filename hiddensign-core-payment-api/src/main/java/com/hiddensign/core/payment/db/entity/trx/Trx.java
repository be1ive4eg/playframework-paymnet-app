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
@Table(name = Trx.TABLE)
public class Trx extends EbeanModel {
    public static final String TABLE = "TBL_TRX";

    @Column(name = "from_acc")
    private long fromAcc;

    @Column(name = "from_ccy")
    @Enumerated(EnumType.STRING)
    private CurrencyType fromCcy;

    @Column(name = "from_amount", precision = 19, scale = 4)
    private BigDecimal fromAmount;

    @Column(name = "to_acc")
    private long toAcc;

    @Column(name = "to_ccy")
    @Enumerated(EnumType.STRING)
    private CurrencyType toCcy;

    @Column(name = "to_amount", precision = 19, scale = 4)
    private BigDecimal toAmount;

    @Column(name = "currency_rate")
    private double currencyRate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TrxType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusType status;

    public long getFromAcc() {
        return fromAcc;
    }

    public void setFromAcc(long fromAcc) {
        this.fromAcc = fromAcc;
    }

    public CurrencyType getFromCcy() {
        return fromCcy;
    }

    public void setFromCcy(CurrencyType fromCcy) {
        this.fromCcy = fromCcy;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public long getToAcc() {
        return toAcc;
    }

    public void setToAcc(long toAcc) {
        this.toAcc = toAcc;
    }

    public CurrencyType getToCcy() {
        return toCcy;
    }

    public void setToCcy(CurrencyType toCcy) {
        this.toCcy = toCcy;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public TrxType getType() {
        return type;
    }

    public void setType(TrxType type) {
        this.type = type;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Trx{" +
                "fromAcc=" + fromAcc +
                ", fromCcy=" + fromCcy +
                ", fromAmount=" + fromAmount +
                ", toAcc=" + toAcc +
                ", toCcy=" + toCcy +
                ", toAmount=" + toAmount +
                ", currencyRate=" + currencyRate +
                ", type=" + type +
                ", status=" + status +
                "} " + super.toString();
    }
}
