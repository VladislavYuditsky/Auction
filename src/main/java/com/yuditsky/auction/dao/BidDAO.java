package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Bid;

import java.util.List;

public interface BidDAO extends GenericDAO<Bid> {

    List<Bid> findByAuctionId(int auctionId) throws DAOException;

    List<Bid> findByBidderId(int bidderId) throws DAOException;

    Bid findWithMaxSumByAuctionId(int auctionId) throws DAOException;

    Bid findWithMinSumByBidderIdAndAuctionId(int bidderId, int auctionId) throws DAOException;

    Bid findWithMinSumByAuctionId(int auctionId) throws DAOException;
}
