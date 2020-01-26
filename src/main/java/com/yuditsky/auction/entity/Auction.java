package com.yuditsky.auction.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Auction implements Identified{
    private int id;
    private AuctionType type;
    private int lotId;
    private List<Bid> bids;
    private LocalDateTime finishTime;

    public Auction(int id, AuctionType type, int lotId, List<Bid> bids, LocalDateTime finishTime) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == auction.id &&
                type == auction.type &&
                lotId == auction.lotId &&
                bids.equals(auction.bids) &&
                finishTime.equals(auction.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, lotId, bids, finishTime);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", type=" + type +
                ", lotId=" + lotId +
                ", bids=" + bids +
                ", finishTime=" + finishTime +
                '}';
    }
}
