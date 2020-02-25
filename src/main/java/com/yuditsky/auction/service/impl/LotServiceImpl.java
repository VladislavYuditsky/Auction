package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.LotDAO;
import com.yuditsky.auction.entity.*;
import com.yuditsky.auction.service.*;
import com.yuditsky.auction.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class LotServiceImpl implements LotService {
    private final static Logger logger = LogManager.getLogger(LotServiceImpl.class);

    private final LotDAO lotDAO;
    private final Validator validator;

    public LotServiceImpl() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        lotDAO = daoFactory.getLotDAO();

        validator = new Validator();
    }

    @Override
    public void save(Lot lot) throws ServiceException {
        if (!validator.checkLot(lot)) {
            throw new ServiceException("Invalid lot data");
        }

        try {
            lotDAO.save(lot);
        } catch (DAOException e) {
            logger.error("Can't save lot", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Lot> findByOwnerId(int id) throws ServiceException {
        try {
            return lotDAO.findByOwnerId(id);
        } catch (DAOException e) {
            logger.error("Can't find lots by owner id = " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Lot findById(int id) throws ServiceException {
        try {
            return lotDAO.findById(id);
        } catch (DAOException e) {
            logger.error("Can't find lot by id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Lot> findAwaitingPaymentLots(int userId) throws ServiceException {
        try {
            return lotDAO.findAwaitingPaymentLots(userId);
        } catch (DAOException e) {
            logger.error("Can't find awaiting payment lot by user id=" + userId, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Lot> findActiveLotsByAuctionType(AuctionType type) throws ServiceException {
        try {
            return lotDAO.findActiveLotByAuctionType(type);
        } catch (DAOException e) {
            logger.error("Can't find active lot by auction type=" + type, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Lot> findLotsWithUserBids(int userId) throws ServiceException {
        try {
            return lotDAO.findLotsWithUserBids(userId);
        } catch (DAOException e) {
            logger.error("Can't find lots with user bids", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean buy(Lot lot, User buyer) throws ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuctionService auctionService = serviceFactory.getAuctionService();
        BidService bidService = serviceFactory.getBidService();
        PaymentService paymentService = serviceFactory.getPaymentService();

        Auction auction = auctionService.findByLotId(lot.getId());
        Bid bid = bidService.findMinByBidderIdAndAuctionId(buyer.getId(), auction.getId());

        BigDecimal userBalance = buyer.getBalance();
        BigDecimal price;

        if (bid != null) {
            price = bid.getSum();
        } else {
            price = lot.getStartPrice();
        }

        if (auction.getStatus() == AuctionStatus.ACTIVE && userBalance.compareTo(price) > 0) {
            auctionService.changeStatus(auction);
            return paymentService.createPayment(lot, buyer);
        }

        return false;
    }

    @Override
    public void changeOwner(Lot lot, User user) throws ServiceException {
        lot.setOwnerId(user.getId());
        update(lot);
    }

    @Override
    public void update(Lot lot) throws ServiceException {
        if (!validator.checkLot(lot)) {
            throw new ServiceException("Invalid lot data");
        }

        try {
            lotDAO.update(lot);
        } catch (DAOException e) {
            logger.error("Can't  update lot with id=" + lot.getId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Lot lot) throws ServiceException {
        try {
            lotDAO.delete(lot);
        } catch (DAOException e) {
            logger.error("Can't  delete lot with id=" + lot.getId(), e);
            throw new ServiceException(e);
        }
    }
}
