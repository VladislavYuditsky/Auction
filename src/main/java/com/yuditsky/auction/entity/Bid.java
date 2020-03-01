package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bid implements Identified {
    private int id;
    private int bidderId;
    private BigDecimal sum;
    private LocalDateTime time;
    private int auctionId;

    public Bid(int id, int bidderId, BigDecimal sum, LocalDateTime time, int auctionId) {
        this.id = id;
        this.bidderId = bidderId;
        this.sum = sum;
        this.time = time;
        this.auctionId = auctionId;
    }

    public Bid(int bidderId, BigDecimal sum, LocalDateTime time, int auctionId) {
        this.bidderId = bidderId;
        this.sum = sum;
        this.time = time;
        this.auctionId = auctionId;
    }

    public Bid() {
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return id == bid.id &&
                bidderId == bid.bidderId &&
                sum.equals(bid.sum) &&
                time.equals(bid.time) &&
                auctionId == bid.auctionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bidderId, sum, time, auctionId);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", bidderId=" + bidderId +
                ", sum=" + sum +
                ", time=" + time +
                ", auctionId=" + auctionId +
                '}';
    }
}
