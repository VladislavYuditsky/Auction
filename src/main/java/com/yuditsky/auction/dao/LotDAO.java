package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Lot;

import java.math.BigDecimal;

public interface LotDAO {
    void addLot(Lot lot) throws DAOException;

    void deleteLotById(int lotId) throws DAOException;

    Lot findLotById(int lotId) throws DAOException;

    void changeDescription(int lotId, String newDescription) throws DAOException;

    void changeLocation(int lotId, String newLocation) throws DAOException;

    void changeStartPrice(int lotId, BigDecimal newStartPrice) throws DAOException;

    void changeMinPrice(int lotId, BigDecimal newMinPrice) throws DAOException;
}
