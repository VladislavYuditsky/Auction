package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Lot implements Identified{
    private int id;
    private String name;
    private String description;
    private String location;
    private int ownerId;
    private int buyerId;
    private BigDecimal startPrice;
    private BigDecimal minPrice;

    public Lot(int id, String name, String description, String location, int ownerId, int buyerId, BigDecimal startPrice,
               BigDecimal minPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.ownerId = ownerId;
        this.buyerId = buyerId;
        this.startPrice = startPrice;
        this.minPrice = minPrice;
    }

    public Lot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
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
        return id == lot.id &&
                ownerId == lot.ownerId &&
                buyerId == lot.buyerId &&
                name.equals(lot.name) &&
                description.equals(lot.description) &&
                location.equals(lot.location) &&
                startPrice.equals(lot.startPrice) &&
                minPrice.equals(lot.minPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, location, ownerId, buyerId, startPrice, minPrice);
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", ownerId=" + ownerId +
                ", buyerId=" + buyerId +
                ", startPrice=" + startPrice +
                ", minPrice=" + minPrice +
                '}';
    }
}
