package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Lot;

import java.math.BigDecimal;
import java.util.List;

public interface LotDAO {
    void save(Lot lot) throws DAOException;

    void delete(Lot lot) throws DAOException;

    Lot findById(int lotId) throws DAOException;

    List<Lot> findBySellerId(int sellerId) throws DAOException;

    void updateDescription(Lot lot, String newDescription) throws DAOException;

    void updateLocation(Lot lot, String newLocation) throws DAOException;

    void updateStartPrice(Lot lot, BigDecimal newStartPrice) throws DAOException;

    void updateMinPrice(Lot lot, BigDecimal newMinPrice) throws DAOException;
}
