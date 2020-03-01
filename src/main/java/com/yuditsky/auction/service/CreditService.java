package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface CreditService {
    void save(Credit credit) throws ServiceException;

    Credit findById(int id) throws ServiceException;

    List<Credit> findByBorrowerId(int id) throws ServiceException;

    List<Credit> findByBorrowerId(int id, int limit, int offset) throws ServiceException;

    boolean subtractBalance(int creditId, BigDecimal sum, User borrower) throws ServiceException;

    void updateBalance(Credit credit, BigDecimal balance) throws ServiceException;

    void createCredit(int borrowerId, BigDecimal sum) throws ServiceException;

    List<Integer> findDebtors() throws ServiceException;
}
