package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentDAO {
    void addPayment(Payment payment) throws DAOException;

    Payment findPaymentById(int paymentId) throws DAOException;

    List<Integer> findPaymentIdsByPayerId(int payerId) throws DAOException;

    void changePayerId(Payment payment, int newPayerId) throws DAOException;

    void changeSum(Payment payment, BigDecimal newSum) throws DAOException;

    void changeLotId(Payment payment, int newLotId) throws DAOException;

    void changeDate(Payment payment, LocalDateTime newDate) throws DAOException;

    void deletePayment(Payment payment) throws DAOException;
}
