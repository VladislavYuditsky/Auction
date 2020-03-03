package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.CreditDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import com.yuditsky.auction.service.impl.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.yuditsky.auction.service.impl.util.Constant.CREDIT_PERCENT;
import static com.yuditsky.auction.service.impl.util.Constant.NULL;
import static java.math.BigDecimal.ROUND_DOWN;

public class CreditServiceImpl implements CreditService {
    private final static Logger logger = LogManager.getLogger(CreditServiceImpl.class);

    private final CreditDAO creditDAO;
    private final Validator validator;

    public CreditServiceImpl() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        creditDAO = daoFactory.getCreditDAO();

        validator = new Validator();
    }

    @Override
    public void save(Credit credit) throws ServiceException {
        try {
            creditDAO.save(credit);
        } catch (DAOException e) {
            logger.error("Can't save credit", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Credit findById(int id) throws ServiceException {
        try {
            return creditDAO.findById(id);
        } catch (DAOException e) {
            logger.error("Can't find credit with id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Credit> findByBorrowerId(int id) throws ServiceException {
        try {
            return creditDAO.findByBorrowerId(id);
        } catch (DAOException e) {
            logger.error("Can't find credits by borrower id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Credit> findByBorrowerId(int id, int limit, int offset) throws ServiceException {
        try {
            return creditDAO.findByBorrowerId(id, limit, offset);
        } catch (DAOException e) {
            logger.error("Can't find credits by borrower id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean subtractBalance(int creditId, BigDecimal sum, User borrower) throws ServiceException {
        if (!validator.checkBigDecimal(sum)) {
            throw new ServiceException("Invalid sum");
        }

        if (borrower.getBalance().compareTo(sum) >= 0) {

            Credit credit = findById(creditId);

            BigDecimal newBalance = credit.getBalance().subtract(sum).setScale(4, ROUND_DOWN);

            if (newBalance.compareTo(NULL) >= 0) {
                updateBalance(credit, newBalance);

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                UserService userService = serviceFactory.getUserService();

                userService.subtractBalance(borrower, sum);
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateBalance(Credit credit, BigDecimal balance) throws ServiceException {
        try {
            creditDAO.updateBalance(credit, balance);
        } catch (DAOException e) {
            logger.error("Can't update balance for credit " + credit.getId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createCredit(int borrowerId, BigDecimal sum) throws ServiceException {
        BigDecimal creditSum = sum.multiply(CREDIT_PERCENT);
        LocalDateTime endDate = LocalDateTime.now().plusYears(1);

        Credit credit = new Credit(CREDIT_PERCENT, endDate, creditSum, creditSum, borrowerId);

        save(credit);
    }

    @Override
    public List<Integer> findDebtors() throws ServiceException {
        try {
            return creditDAO.findDebtors();
        } catch (DAOException e){
            logger.error("Can't blockDebtors debtors", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void blockDebtors() throws ServiceException {
        try {
            creditDAO.blockDebtors();
        } catch (DAOException e) {
            logger.error("Can't blockDebtors debtors", e);
            throw new ServiceException(e);
        }
    }
}
