package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.CreditDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_DOWN;

public class CreditServiceImpl implements CreditService {
    private final static Logger logger = LogManager.getLogger(CreditServiceImpl.class);

    @Override
    public void save(Credit credit) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        CreditDAO creditDAO = factory.getCreditDAO();
        try {
            creditDAO.save(credit);
        } catch (DAOException e) {
            logger.error("Can't save credit", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Credit> findByBorrowerId(int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        CreditDAO creditDAO = factory.getCreditDAO();
        try {
            return creditDAO.findByBorrowerId(id);
        } catch (DAOException e) {
            logger.error("Can't find credits by borrower id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean subtractBalance(Credit credit, BigDecimal sum) throws ServiceException {
        BigDecimal newBalance = credit.getBalance().subtract(sum).setScale(4, ROUND_DOWN);
        BigDecimal NULL = new BigDecimal("0"); //const

        if(newBalance.compareTo(NULL) < 0){
            return false;
        }

        updateBalance(credit, newBalance);

        return true;
    }

    @Override
    public void updateBalance(Credit credit, BigDecimal balance) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        CreditDAO creditDAO = factory.getCreditDAO();
        try {
            creditDAO.updateBalance(credit, balance);
        } catch (DAOException e) {
            logger.error("Can't update balance for credit " + credit.getCreditId(), e);
            throw new ServiceException(e);
        }
    }
}
