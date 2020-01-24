package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;

import java.time.LocalDateTime;

public interface AuctionDAO {
    void save(Auction auction) throws DAOException;

    Auction findById(int auctionId) throws DAOException;

    void updateAuctionType(Auction auction, AuctionType newType) throws DAOException;

    void updateLotId(Auction auction, int newLotId) throws DAOException;

    void updateFinishTime(Auction auction, LocalDateTime newFinishTime) throws DAOException;

    void delete(Auction auction) throws DAOException;
}
