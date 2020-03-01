package com.yuditsky.auction.entity;

import java.util.Objects;

public class Auction implements Identified {
    private int id;
    private AuctionType type;
    private int lotId;
    private AuctionStatus status;
    private int winnerId;

    public Auction(int id, AuctionType type, int lotId, AuctionStatus status, int winnerId) {
        this.id = id;
        this.type = type;
        this.lotId = lotId;
        this.status = status;
        this.winnerId = winnerId;
    }

    public Auction(AuctionType type, int lotId, AuctionStatus status) {
        this.type = type;
        this.lotId = lotId;
        this.status = status;
    }

    public Auction() {
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
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

    public AuctionStatus getStatus() {
        return status;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return id == auction.id &&
                lotId == auction.lotId &&
                winnerId == auction.winnerId &&
                type == auction.type &&
                status == auction.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, lotId, status, winnerId);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", type=" + type +
                ", lotId=" + lotId +
                ", status=" + status +
                ", winnerId=" + winnerId +
                '}';
    }
}
