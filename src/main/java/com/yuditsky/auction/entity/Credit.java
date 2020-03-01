package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Credit implements Identified {
    private int id;
    private BigDecimal percent;
    private LocalDateTime endDate;
    private BigDecimal balance;
    private BigDecimal sum;
    private int borrowerId;

    public Credit(int id, BigDecimal percent, LocalDateTime endDate, BigDecimal balance, BigDecimal sum, int borrowerId) {
        this.id = id;
        this.percent = percent;
        this.endDate = endDate;
        this.balance = balance;
        this.sum = sum;
        this.borrowerId = borrowerId;
    }

    public Credit(BigDecimal percent, LocalDateTime endDate, BigDecimal balance, BigDecimal sum, int borrowerId) {
        this.percent = percent;
        this.endDate = endDate;
        this.balance = balance;
        this.sum = sum;
        this.borrowerId = borrowerId;
    }

    public Credit() {
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
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
        return id == credit.id &&
                percent.equals(credit.percent) &&
                borrowerId == credit.borrowerId &&
                endDate.equals(credit.endDate) &&
                balance.equals(credit.balance) &&
                sum.equals(credit.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, percent, endDate, balance, sum, borrowerId);
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", percent=" + percent +
                ", endDate=" + endDate +
                ", balance=" + balance +
                ", sum=" + sum +
                ", borrowerId=" + borrowerId +
                '}';
    }
}
