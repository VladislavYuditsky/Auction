package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Credit;

import java.math.BigDecimal;
import java.util.List;

public interface CreditDAO extends GenericDAO<Credit>{

    List<Credit> findByBorrowerId(int borrowerId) throws DAOException;

    void updateBalance(Credit credit, BigDecimal newBalance) throws DAOException;
}
