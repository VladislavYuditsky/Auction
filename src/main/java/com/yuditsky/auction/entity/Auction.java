package com.yuditsky.auction.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Auction {
    private int auctionId;
    private AuctionType type;
    private Lot lot;
    private List<Bid> bids; //mb tolko last
    private LocalDateTime finishTime;

    public Auction(int auctionId, AuctionType type, Lot lot, List<Bid> bids, LocalDateTime finishTime) {
        this.auctionId = auctionId;
        this.type = type;
        this.lot = lot;
        this.bids = bids;
        this.finishTime = finishTime;
    }

    public Auction() {
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public AuctionType getType() {
        return type;
    }

    public void setType(AuctionType type) {
        this.type = type;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return auctionId == auction.auctionId &&
                type == auction.type &&
                lot.equals(auction.lot) &&
                bids.equals(auction.bids) &&
                finishTime.equals(auction.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionId, type, lot, bids, finishTime);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", type=" + type +
                ", lot=" + lot +
                ", bids=" + bids +
                ", finishTime=" + finishTime +
                '}';
    }
}
