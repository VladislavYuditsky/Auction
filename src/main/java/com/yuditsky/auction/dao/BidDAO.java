package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BidDAO {
    void save(Bid bid) throws DAOException;

    Bid findById(int bidId) throws DAOException;

    List<Bid> findByAuctionId(int auctionId) throws DAOException;

    List<Bid> findByBidderId(int bidderId) throws DAOException;

    void updateBidderId(Bid bid, int newBidderId) throws DAOException;

    void updateSum(Bid bid, BigDecimal newSum) throws DAOException;

    void updateTime(Bid bid, LocalDateTime newTime) throws DAOException;

    void updateAuctionId(Bid bid, int newAuctionId) throws DAOException;

    void delete(Bid bid) throws DAOException;
}
