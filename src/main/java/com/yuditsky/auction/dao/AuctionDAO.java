package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;

import java.time.LocalDateTime;

public interface AuctionDAO {
    void addAuction(Auction auction) throws DAOException;

    Auction findAuctionById(int auctionId) throws DAOException;

    void changeAuctionType(Auction auction, AuctionType newType) throws DAOException;

    void changeLotId(Auction auction, int newLotId) throws DAOException;

    void changeFinishTime(Auction auction, LocalDateTime newFinishTime) throws DAOException;

    void deleteAuction(Auction auction) throws DAOException;
}
