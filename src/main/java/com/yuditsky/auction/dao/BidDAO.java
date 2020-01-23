package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;

import java.math.BigDecimal;

public interface BidDAO {
    void addBid(Bid bid) throws DAOException;

    Bid findBidById(int bidId) throws DAOException;

    void changeBidderId(int bidId, String newBidderId) throws DAOException;

    void changeSum(int bidId, String newSum) throws DAOException;

    void changeTime(int bidId, String newTime) throws DAOException;

    void changeAuctionId(int bidId, String newAuctionId) throws DAOException;

    void deleteBidById(int bidId) throws DAOException;
}
