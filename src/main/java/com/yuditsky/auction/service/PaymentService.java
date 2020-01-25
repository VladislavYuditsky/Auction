package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.Payment;

import java.util.List;

public interface PaymentService {
    void save(Payment payment) throws ServiceException;

    List<Payment> findByPayerId(int id) throws ServiceException;
}
