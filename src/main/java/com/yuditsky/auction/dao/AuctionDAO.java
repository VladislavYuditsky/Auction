package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;

import java.time.LocalDateTime;

public interface AuctionDAO {
    void addAuction(Auction auction) throws DAOException;

    Auction findAuctionById(int auctionId) throws DAOException;

    void changeAuctionType(int auctionId, AuctionType newType) throws DAOException;

    void changeLot(int auctionId, Lot newLot) throws DAOException;

    void changeFinishTime(int auctionId, LocalDateTime newFinishTime) throws DAOException;

    void deleteAuction(int auctionId) throws DAOException;
}
