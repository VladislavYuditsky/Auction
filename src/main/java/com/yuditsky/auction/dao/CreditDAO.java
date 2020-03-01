package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Credit;

import java.math.BigDecimal;
import java.util.List;

public interface CreditDAO extends GenericDAO<Credit> {

    List<Credit> findByBorrowerId(int borrowerId) throws DAOException;

    List<Credit> findByBorrowerId(int borrowerId, int limit, int offset) throws DAOException;

    void updateBalance(Credit credit, BigDecimal newBalance) throws DAOException;

    List<Integer> findDebtors() throws DAOException;
}
