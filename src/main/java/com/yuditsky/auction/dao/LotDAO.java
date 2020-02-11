package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Lot;

import java.math.BigDecimal;
import java.util.List;

public interface LotDAO extends GenericDAO<Lot>{

    List<Lot> findByOwnerId(int id) throws DAOException;

    /*void updateDescription(Lot lot, String description) throws DAOException;

    void updateLocation(Lot lot, String location) throws DAOException;

    void updateStartPrice(Lot lot, BigDecimal startPrice) throws DAOException;

    void updateMinPrice(Lot lot, BigDecimal minPrice) throws DAOException;*/
}
