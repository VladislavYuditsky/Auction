package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Bid;

import java.util.List;

public interface BidService {
    void save (Bid bid) throws ServiceException;

    List<Bid> findByAuctionId(int id) throws ServiceException;

    List<Bid> findByBidderId(int id) throws ServiceException;

    void delete(Bid bid) throws ServiceException;
}
