package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Payment;

import java.util.List;

public interface PaymentDAO extends GenericDAO<Payment> {

    List<Payment> findByPayerId(int payerId) throws DAOException;

    List<Payment> findByPayerId(int payerId, int limit, int offset) throws DAOException;
}
