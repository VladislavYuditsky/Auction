package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Credit {
    private int creditId;
    private Payment payment;
    private double percent;
    private LocalDate endDate;
    private BigDecimal balance;

    public Credit(int creditId, Payment payment, double percent, LocalDate endDate, BigDecimal balance) {
        this.creditId = creditId;
        this.payment = payment;
        this.percent = percent;
        this.endDate = endDate;
        this.balance = balance;
    }

    public Credit() {
    }

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return creditId == credit.creditId &&
                Double.compare(credit.percent, percent) == 0 &&
                payment.equals(credit.payment) &&
                endDate.equals(credit.endDate) &&
                balance.equals(credit.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditId, payment, percent, endDate, balance);
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditId=" + creditId +
                ", payment=" + payment +
                ", percent=" + percent +
                ", endDate=" + endDate +
                ", balance=" + balance +
                '}';
    }
}
