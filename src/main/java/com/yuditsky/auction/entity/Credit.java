package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Credit {
    private int creditId;
    private double percent;
    private LocalDateTime endDate;
    private BigDecimal balance;
    private BigDecimal sum;
    private int borrowerId;

    public Credit(int creditId, double percent, LocalDateTime endDate, BigDecimal balance, BigDecimal sum, int borrowerId) {
        this.creditId = creditId;
        this.percent = percent;
        this.endDate = endDate;
        this.balance = balance;
        this.sum = sum;
        this.borrowerId = borrowerId;
    }

    public Credit() {
    }

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return creditId == credit.creditId &&
                Double.compare(credit.percent, percent) == 0 &&
                borrowerId == credit.borrowerId &&
                endDate.equals(credit.endDate) &&
                balance.equals(credit.balance) &&
                sum.equals(credit.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditId, percent, endDate, balance, sum, borrowerId);
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditId=" + creditId +
                ", percent=" + percent +
                ", endDate=" + endDate +
                ", balance=" + balance +
                ", sum=" + sum +
                ", borrowerId=" + borrowerId +
                '}';
    }
}
