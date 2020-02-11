package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.Payment;
import com.yuditsky.auction.entity.User;

import java.util.List;

public interface PaymentService {
    void save(Payment payment) throws ServiceException;

    List<Payment> findByPayerId(int id) throws ServiceException;

    boolean createPayment(Lot lot, User payer) throws ServiceException;
}
