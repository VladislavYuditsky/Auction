package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BidDAO {
    void addBid(Bid bid) throws DAOException;

    Bid findBidById(int bidId) throws DAOException;

    void changeBidderId(int bidId, int newBidderId) throws DAOException;

    void changeSum(int bidId, BigDecimal newSum) throws DAOException;

    void changeTime(int bidId, LocalDateTime newTime) throws DAOException;

    void changeAuctionId(int bidId, int newAuctionId) throws DAOException;

    void deleteBidById(int bidId) throws DAOException;
}
