package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface BidService {
    void save (Bid bid) throws ServiceException;

    List<Bid> findByAuctionId(int id) throws ServiceException;

    List<Bid> findByBidderId(int id) throws ServiceException;

    Bid findWithMaxSumByAuctionId(int id) throws ServiceException;

    Bid findMinByBidderIdAndAuctionId(int bidderId, int auctionId) throws ServiceException;

    Bid findWithMinSumByAuctionId(int id) throws ServiceException;

    void delete(Bid bid) throws ServiceException;

    boolean isMaxBid(Bid bid) throws ServiceException;

    boolean isRebid(Bid bid) throws ServiceException;

    boolean createBid(Lot lot, User bidder) throws ServiceException;

    boolean createBid(Lot lot, User bidder, BigDecimal bidSum) throws ServiceException;
}
