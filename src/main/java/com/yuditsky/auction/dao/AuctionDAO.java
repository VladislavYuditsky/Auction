package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;

import java.util.List;

public interface AuctionDAO extends GenericDAO<Auction> {

    List<Auction> findByType(AuctionType type) throws DAOException;

    List<Auction> findByStatus(AuctionStatus status) throws DAOException;

    List<Auction> findByStatus(AuctionStatus status, int limit, int offset) throws DAOException;

    List<Auction> findByWinnerId(int id) throws DAOException;

    Auction findByLotId(int id) throws DAOException;
}
