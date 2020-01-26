package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionDAO extends GenericDAO<Auction> {

    List<Auction> findByType(AuctionType type) throws DAOException;

    /*void updateAuctionType(Auction auction, AuctionType newType) throws DAOException;

    void updateLotId(Auction auction, int newLotId) throws DAOException;

    void updateFinishTime(Auction auction, LocalDateTime newFinishTime) throws DAOException;*/
}
