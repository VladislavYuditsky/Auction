package com.yuditsky.auction.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Auction {
    private int auctionId;
    private AuctionType type;
    private int lotId;
    private List<Bid> bids;
    private LocalDateTime finishTime;

    public Auction(int auctionId, AuctionType type, int lotId, List<Bid> bids, LocalDateTime finishTime) {
        this.auctionId = auctionId;
        this.type = type;
        this.lotId = lotId;
        this.bids = bids;
        this.finishTime = finishTime;
    }

    public Auction(AuctionType type, int lotId, LocalDateTime finishTime) {
        this.type = type;
        this.lotId = lotId;
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

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
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
                lotId == auction.lotId &&
                bids.equals(auction.bids) &&
                finishTime.equals(auction.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionId, type, lotId, bids, finishTime);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", type=" + type +
                ", lotId=" + lotId +
                ", bids=" + bids +
                ", finishTime=" + finishTime +
                '}';
    }
}
