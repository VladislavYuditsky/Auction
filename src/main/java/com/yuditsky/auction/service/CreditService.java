package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Credit;

import java.math.BigDecimal;
import java.util.List;

public interface CreditService {
    void save(Credit credit) throws ServiceException;

    List<Credit> findByBorrowerId(int id) throws ServiceException;

    boolean subtractBalance(Credit credit, BigDecimal sum) throws ServiceException;

    void updateBalance(Credit credit, BigDecimal balance) throws ServiceException;
}
