package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;

import javax.swing.*;
import java.util.List;

public interface LotService {
    void save (Lot lot) throws ServiceException;

    List<Lot> findByOwnerId(int id) throws ServiceException;

    Lot findById(int id) throws ServiceException;

    List<Lot> findAwaitingPaymentLots(int userId) throws ServiceException;

    List<Lot> findActiveLotsByAuctionType(AuctionType type) throws ServiceException;

    List<Lot> findLotsWithUserBids(int userId) throws ServiceException;

    boolean buy(Lot lot, User buyer) throws ServiceException;

    void changeOwner(Lot lot, User user) throws ServiceException;

    void update(Lot lot) throws ServiceException;

    void delete(Lot lot) throws ServiceException;
}
