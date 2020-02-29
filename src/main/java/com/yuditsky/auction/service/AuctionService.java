package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionService {
    void save(Auction auction) throws ServiceException;

    Auction findById(int id) throws ServiceException;

    List<Auction> findByType(AuctionType type) throws ServiceException;

    List<Auction> findByStatus(AuctionStatus status) throws ServiceException;

    List<Auction> findByStatus(AuctionStatus status, int limit, int offset) throws ServiceException;

    List<Auction> findByWinnerId(int id) throws ServiceException;

    Auction findByLotId(int id) throws ServiceException;

    void update(Auction auction) throws ServiceException;

    void delete(Auction auction) throws ServiceException;

    void finishAuction(Auction auction) throws ServiceException;

    void changeStatus(Auction auction) throws ServiceException;

    boolean createAuction(int lotId, AuctionType type) throws ServiceException;
}
