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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LotServiceImpl implements LotService {
    private final static Logger logger = LogManager.getLogger(LotServiceImpl.class);

    private final LotDAO lotDAO;
    private final Validator validator;

    public LotServiceImpl() {
        DAOFactory factory = DAOFactory.getInstance();
        lotDAO = factory.getLotDAO();

        validator = new Validator();
    }

    @Override
    public void save(Lot lot) throws ServiceException {
        if(!validator.checkLot(lot)){
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
        ServiceFactory factory = ServiceFactory.getInstance();
        AuctionService auctionService = factory.getAuctionService();

        List<Auction> auctions = auctionService.findByWinnerId(userId);

        List<Lot> lots = new ArrayList<>();
        for (Auction auction : auctions) {
            Lot lot = findById(auction.getLotId());
            lots.add(lot);
        }

        return lots;
    }

    @Override
    public List<Lot> findActiveLotsByAuctionType(AuctionType type) throws ServiceException {
        ServiceFactory factory = ServiceFactory.getInstance();
        AuctionService auctionService = factory.getAuctionService();

        List<Auction> auctions = auctionService.findByType(type);

        List<Lot> lots = new ArrayList<>();

        for (Auction auction : auctions) {
            if (auction.getStatus() == AuctionStatus.ACTIVE) {
                Lot lot = findById(auction.getLotId());
                if (lot != null) {
                    lots.add(lot);
                }
            }
        }

        return lots;
    }

    @Override
    public List<Lot> takeLotsWithUserBids(int userId) throws ServiceException {
        ServiceFactory factory = ServiceFactory.getInstance();
        BidService bidService = factory.getBidService();

        List<Bid> bids = bidService.findByBidderId(userId);

        AuctionService auctionService = factory.getAuctionService();

        List<Auction> auctions = new ArrayList<>();
        for (Bid bid : bids) {
            Auction auction = auctionService.findById(bid.getAuctionId());
            if (!auctions.contains(auction)) {
                auctions.add(auction);
            }
        }

        List<Lot> lots = new ArrayList<>();
        for (Auction auction : auctions) {
            Lot lot = findById(auction.getLotId());
            lots.add(lot);
        }

        return lots;
    }

    @Override
    public boolean buy(Lot lot, User buyer) throws ServiceException {
        ServiceFactory factory = ServiceFactory.getInstance();
        BidService bidService = factory.getBidService();
        AuctionService auctionService = factory.getAuctionService();
        UserService userService = factory.getUserService();
        PaymentService paymentService = factory.getPaymentService();

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

            userService.subtractBalance(buyer, price);

            User owner = userService.findById(lot.getOwnerId());
            userService.addBalance(owner, lot.getStartPrice());

            changeOwner(lot, buyer);

            Payment payment = new Payment(buyer.getId(), price, lot.getId(), LocalDateTime.now()); //////////
            paymentService.save(payment);

            auctionService.delete(auction);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void changeOwner(Lot lot, User user) throws ServiceException {
            lot.setOwnerId(user.getId());
            update(lot);
    }

    @Override
    public void update(Lot lot) throws ServiceException {
        if(!validator.checkLot(lot)){
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
