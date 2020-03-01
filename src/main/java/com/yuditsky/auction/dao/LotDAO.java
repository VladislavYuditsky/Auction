package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;

import java.util.List;

public interface LotDAO extends GenericDAO<Lot> {

    List<Lot> findByOwnerId(int id) throws DAOException;

    List<Lot> findByOwnerId(int id, int limit, int offset) throws DAOException;

    List<Lot> findAwaitingPaymentLots(int userId) throws DAOException;

    List<Lot> findAwaitingPaymentLots(int userId, int limit, int offset) throws DAOException;

    List<Lot> findActiveLotByAuctionType(AuctionType type) throws DAOException;

    List<Lot> findActiveLotByAuctionType(AuctionType type, int limit, int offset) throws DAOException;

    List<Lot> findLotsWithUserBids(int userId) throws DAOException;

    List<Lot> findLotsWithUserBids(int userId, int limit, int offset) throws DAOException;
}
