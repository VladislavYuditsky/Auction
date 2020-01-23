package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bid {
    private int bidId;
    private int bidderId;
    private BigDecimal sum;
    private LocalDateTime time;
    private int auctionId;

    public Bid(int bidId, int bidderId, BigDecimal sum, LocalDateTime time, int auctionId) {
        this.bidId = bidId;
        this.bidderId = bidderId;
        this.sum = sum;
        this.time = time;
        this.auctionId = auctionId;
    }

    public Bid() {
    }

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
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
        return bidId == bid.bidId &&
                bidderId == bid.bidderId &&
                sum.equals(bid.sum) &&
                time.equals(bid.time) &&
                auctionId == bid.auctionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bidId, bidderId, sum, time, auctionId);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", bidderId=" + bidderId +
                ", sum=" + sum +
                ", time=" + time +
                ", auctionId=" + auctionId +
                '}';
    }
}
