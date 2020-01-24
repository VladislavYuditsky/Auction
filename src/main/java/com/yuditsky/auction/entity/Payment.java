package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private int paymentId;
    private int payerId;
    private BigDecimal sum;
    private int lotId;
    private LocalDateTime date;

    public Payment(int paymentId, int payerId, BigDecimal sum, int lotId, LocalDateTime date) {
        this.paymentId = paymentId;
        this.payerId = payerId;
        this.sum = sum;
        this.lotId = lotId;
        this.date = date;
    }

    public Payment() {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return paymentId == payment.paymentId &&
                payerId == payment.payerId &&
                lotId == payment.lotId &&
                sum.equals(payment.sum) &&
                date.equals(payment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, payerId, sum, lotId, date);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", payerId=" + payerId +
                ", sum=" + sum +
                ", lotId=" + lotId +
                ", date=" + date +
                '}';
    }
}
