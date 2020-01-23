package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Lot {
    private int lotId;
    private String name;
    private String description;
    private String location;
    private int sellerId;
    private BigDecimal startPrice;
    private BigDecimal minPrice;

    public Lot(int lotId, String name, String description, String location, int sellerId, BigDecimal startPrice,
               BigDecimal minPrice) {
        this.lotId = lotId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.sellerId = sellerId;
        this.startPrice = startPrice;
        this.minPrice = minPrice;
    }

    public Lot() {
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lot lot = (Lot) o;
        return lotId == lot.lotId &&
                name.equals(lot.name) &&
                description.equals(lot.description) &&
                location.equals(lot.location) &&
                sellerId == lot.sellerId &&
                startPrice.equals(lot.startPrice) &&
                minPrice.equals(lot.minPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lotId, name, description, location, sellerId, startPrice, minPrice);
    }

    @Override
    public String toString() {
        return "Lot{" +
                "lotId=" + lotId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", sellerId=" + sellerId +
                ", startPrice=" + startPrice +
                ", minPrice=" + minPrice +
                '}';
    }
}
