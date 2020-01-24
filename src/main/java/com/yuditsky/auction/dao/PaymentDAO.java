package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentDAO {
    void save(Payment payment) throws DAOException;

    Payment findById(int paymentId) throws DAOException;

    List<Payment> findByPayerId(int payerId) throws DAOException;

    void updatePayerId(Payment payment, int newPayerId) throws DAOException;

    void updateSum(Payment payment, BigDecimal newSum) throws DAOException;

    void updateLotId(Payment payment, int newLotId) throws DAOException;

    void updateDate(Payment payment, LocalDateTime newDate) throws DAOException;

    void delete(Payment payment) throws DAOException;
}
