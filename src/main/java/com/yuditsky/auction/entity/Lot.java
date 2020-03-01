package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Lot implements Identified {
    private int id;
    private String name;
    private String description;
    private String location;
    private int ownerId;
    private BigDecimal startPrice;

    public Lot(int id, String name, String description, String location, int ownerId, BigDecimal startPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.ownerId = ownerId;
        this.startPrice = startPrice;
    }

    public Lot(String name, String description, String location, int ownerId, BigDecimal startPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.ownerId = ownerId;
        this.startPrice = startPrice;
    }

    public Lot() {
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
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

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lot lot = (Lot) o;
        return id == lot.id &&
                ownerId == lot.ownerId &&
                name.equals(lot.name) &&
                description.equals(lot.description) &&
                location.equals(lot.location) &&
                startPrice.equals(lot.startPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, location, ownerId, startPrice);
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", ownerId=" + ownerId +
                ", startPrice=" + startPrice +
                '}';
    }
}
