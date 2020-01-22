package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Lot {
    private int lotId;
    private String name;
    private String description;
    private String photo;
    private String location;
    private User seller;
    private BigDecimal startPrice; //mb v auct
    private BigDecimal minPrice; //mb v auct

    public Lot(int lotId, String name, String description, String photo, String location, User seller,
               BigDecimal startPrice, BigDecimal minPrice) {
        this.lotId = lotId;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.location = location;
        this.seller = seller;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
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
                photo.equals(lot.photo) &&
                location.equals(lot.location) &&
                seller.equals(lot.seller) &&
                startPrice.equals(lot.startPrice) &&
                minPrice.equals(lot.minPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lotId, name, description, photo, location, seller, startPrice, minPrice);
    }

    @Override
    public String toString() {
        return "Lot{" +
                "lotId=" + lotId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", location='" + location + '\'' +
                ", seller=" + seller +
                ", startPrice=" + startPrice +
                ", minPrice=" + minPrice +
                '}';
    }
}
