package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.service.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionDAO extends GenericDAO<Auction> {

    List<Auction> findByType(AuctionType type) throws DAOException;

    List<Auction> findByStatus(AuctionStatus status) throws DAOException;

    List<Auction> findByStatus(AuctionStatus status, int limit, int offset) throws DAOException;

    List<Auction> findByWinnerId(int id) throws DAOException;

    Auction findByLotId(int id) throws DAOException;

    /*void updateAuctionType(Auction auction, AuctionType newType) throws DAOException;

    void updateLotId(Auction auction, int newLotId) throws DAOException;

    void updateFinishTime(Auction auction, LocalDateTime newFinishTime) throws DAOException;*/
}
