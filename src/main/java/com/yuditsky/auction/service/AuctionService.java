package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionService {
    void save(Auction auction) throws ServiceException;

    Auction findById(int id) throws ServiceException;

    List<Auction> findByType(AuctionType type) throws ServiceException;

    void updateFinishTime(Auction auction, LocalDateTime finishTime) throws ServiceException;

    void delete(Auction auction) throws ServiceException;
}
