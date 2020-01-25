package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Lot;

import java.util.List;

public interface LotService {
    void save (Lot lot) throws ServiceException;

    List<Lot> findByOwnerId(int id) throws ServiceException;

    List<Lot> findByBuyerId(int id) throws ServiceException;

    Lot findById(int id) throws ServiceException;

    void update(Lot lot) throws ServiceException;

    void delete(Lot lot) throws ServiceException;
}
