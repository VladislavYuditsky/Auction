package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BidDAO {
    void addBid(Bid bid) throws DAOException;

    Bid findBidById(int bidId) throws DAOException;

    List<Integer> findBidIdsByAuctionId(int auctionId) throws DAOException;

    List<Integer> findBidIdsByBidderId(int bidderId) throws DAOException;

    void changeBidderId(Bid bid, int newBidderId) throws DAOException;

    void changeSum(Bid bid, BigDecimal newSum) throws DAOException;

    void changeTime(Bid bid, LocalDateTime newTime) throws DAOException;

    void changeAuctionId(Bid bid, int newAuctionId) throws DAOException;

    void deleteBid(Bid bid) throws DAOException;
}
