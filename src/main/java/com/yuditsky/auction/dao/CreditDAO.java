package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Credit;

import java.util.List;

public interface CreditDAO {
    void save(Credit credit) throws DAOException;

    Credit findById(int creditId) throws DAOException;

    List<Credit> findByBorrowerId(int borrowerId) throws DAOException;

    List<Credit> findAll() throws DAOException;

    void updateBalance(Credit credit, int newBalance) throws DAOException;

    void delete(Credit credit) throws DAOException;
}
