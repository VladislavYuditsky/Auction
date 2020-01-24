package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentDAO {
    void addPayment(Payment payment) throws DAOException;

    Payment findPaymentById(int paymentId) throws DAOException;

    List<Integer> findPaymentIdsByPayerId(int payerId) throws DAOException;

    void changePayerId(int paymentId, int newPayerId) throws DAOException;

    void changeSum(int paymentId, BigDecimal newSum) throws DAOException;

    void changeLotId(int paymentId, int newLotId) throws DAOException;

    void changeDate(int paymentId, LocalDateTime newDate) throws DAOException;

    void deletePaymentById(int paymentId) throws DAOException;
}
