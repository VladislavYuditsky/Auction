package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bid {
    private int bidId;
    private User bidder;
    private BigDecimal sum;
    private LocalDateTime time;
    private Lot lot; //id

    public Bid(int bidId, User bidder, BigDecimal sum, LocalDateTime time, Lot lot) {
        this.bidId = bidId;
        this.bidder = bidder;
        this.sum = sum;
        this.time = time;
        this.lot = lot;
    }

    public Bid() {
    }

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
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
        Bid bid = (Bid) o;
        return bidId == bid.bidId &&
                bidder.equals(bid.bidder) &&
                sum.equals(bid.sum) &&
                time.equals(bid.time) &&
                lot.equals(bid.lot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bidId, bidder, sum, time, lot);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", bidder=" + bidder +
                ", sum=" + sum +
                ", time=" + time +
                ", lot=" + lot +
                '}';
    }
}
