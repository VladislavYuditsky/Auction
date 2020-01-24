package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Lot;

import java.math.BigDecimal;
import java.util.List;

public interface LotDAO {
    void addLot(Lot lot) throws DAOException;

    void deleteLot(Lot lot) throws DAOException;

    Lot findLotById(int lotId) throws DAOException;

    List<Integer> findLotIdsBySellerId(int sellerId) throws DAOException;

    void changeDescription(Lot lot, String newDescription) throws DAOException;

    void changeLocation(Lot lot, String newLocation) throws DAOException;

    void changeStartPrice(Lot lot, BigDecimal newStartPrice) throws DAOException;

    void changeMinPrice(Lot lot, BigDecimal newMinPrice) throws DAOException;
}
