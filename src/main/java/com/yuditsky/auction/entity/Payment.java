package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment implements Identified{
    private int id;
    private int payerId;
    private BigDecimal sum;
    private int lotId;
    private LocalDateTime date;

    public Payment(int id, int payerId, BigDecimal sum, int lotId, LocalDateTime date) {
        this.id = id;
        this.payerId = payerId;
        this.sum = sum;
        this.lotId = lotId;
        this.date = date;
    }

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == payment.id &&
                payerId == payment.payerId &&
                lotId == payment.lotId &&
                sum.equals(payment.sum) &&
                date.equals(payment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payerId, sum, lotId, date);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", payerId=" + payerId +
                ", sum=" + sum +
                ", lotId=" + lotId +
                ", date=" + date +
                '}';
    }
}
