package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.PaymentDAO;
import com.yuditsky.auction.entity.Payment;
import com.yuditsky.auction.service.PaymentService;
import com.yuditsky.auction.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final static Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

    @Override
    public void save(Payment payment) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        try {
            paymentDAO.save(payment);
        } catch (DAOException e) {
            logger.error("Can't save payment", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Payment> findByPayerId(int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        try {
            return paymentDAO.findByPayerId(id);
        } catch (DAOException e) {
            logger.error("Can't find payments by payer id = " + id, e);
            throw new ServiceException(e);
        }
    }
}
