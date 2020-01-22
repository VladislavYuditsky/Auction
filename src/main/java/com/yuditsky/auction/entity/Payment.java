package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Payment {
    private int paymentId;
    private User payer;
    private BigDecimal sum;
    private Lot lot;

    public Payment(int paymentId, User payer, BigDecimal sum, Lot lot) {
        this.paymentId = paymentId;
        this.payer = payer;
        this.sum = sum;
        this.lot = lot;
    }

    public Payment() {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return paymentId == payment.paymentId &&
                payer.equals(payment.payer) &&
                sum.equals(payment.sum) &&
                lot.equals(payment.lot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, payer, sum, lot);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", payer=" + payer +
                ", sum=" + sum +
                ", lot=" + lot +
                '}';
    }
}
