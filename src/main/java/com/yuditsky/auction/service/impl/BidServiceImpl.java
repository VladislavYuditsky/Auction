package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.BidDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.*;
import com.yuditsky.auction.service.*;
import com.yuditsky.auction.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.yuditsky.auction.service.util.Constant.NULL;
import static com.yuditsky.auction.service.util.Constant.REVERS_AUCTION_COEFFICIENT;
import static java.math.BigDecimal.ROUND_DOWN;

public class BidServiceImpl implements BidService {
    private final static Logger logger = LogManager.getLogger(BidServiceImpl.class);

    private final BidDAO bidDAO;
    private final Validator validator;

    public BidServiceImpl() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        bidDAO = daoFactory.getBidDAO();

        validator = new Validator();
    }

    @Override
    public void save(Bid bid) throws ServiceException {
        if (!validator.checkBid(bid)) {
            throw new ServiceException("Invalid bid data");
        }

        try {
            bidDAO.save(bid);
        } catch (DAOException e) {
            logger.error("Can't save bid", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isMaxBid(Bid bid) throws ServiceException {
        Bid maxBid = findWithMaxSumByAuctionId(bid.getAuctionId());

        BigDecimal maxSum = maxBid.getSum();
        BigDecimal curSum = bid.getSum();

        return curSum.compareTo(maxSum) > 0;
    }

    @Override
    public boolean isRebid(Bid bid) throws ServiceException {
        Bid maxBid = findWithMaxSumByAuctionId(bid.getAuctionId());
        return maxBid.getBidderId() == bid.getBidderId();
    }

    @Override
    public List<Bid> findByAuctionId(int id) throws ServiceException {
        try {
            return bidDAO.findByAuctionId(id);
        } catch (DAOException e) {
            logger.error("Can't find bids by auction id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bid findWithMaxSumByAuctionId(int id) throws ServiceException {
        try {
            return bidDAO.findWithMaxSumByAuctionId(id);
        } catch (DAOException e) {
            logger.error("Can't find bid with max sum by auction id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bid findMinByBidderIdAndAuctionId(int bidderId, int auctionId) throws ServiceException {
        try {
            return bidDAO.findWithMinSumByBidderIdAndAuctionId(bidderId, auctionId);
        } catch (DAOException e) {
            logger.error("Can't find bid with min sum by bidder id and auction id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bid findWithMinSumByAuctionId(int id) throws ServiceException {
        try {
            return bidDAO.findWithMinSumByAuctionId(id);
        } catch (DAOException e) {
            logger.error("Can't find bid with min sum by auction id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Bid bid) throws ServiceException {
        try {
            bidDAO.delete(bid);
        } catch (DAOException e) {
            logger.error("Can't delete bid " + bid.getId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean createBid(Lot lot, User bidder) throws ServiceException {
        BigDecimal lotStartPrice = lot.getStartPrice();
        BigDecimal userBalance = bidder.getBalance();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuctionService auctionService = serviceFactory.getAuctionService();
        UserService userService = serviceFactory.getUserService();

        if (lotStartPrice.compareTo(userBalance) < 0) {

            Auction auction = auctionService.findByLotId(lot.getId());

            Bid bid = new Bid(bidder.getId(), NULL, LocalDateTime.now(), auction.getId());
            Bid minBid = findWithMinSumByAuctionId(auction.getId());

            BigDecimal gap = lot.getStartPrice().multiply(REVERS_AUCTION_COEFFICIENT);

            userService.subtractBalance(bidder, gap);

            if (minBid != null) {
                BigDecimal bidSum = minBid.getSum().subtract(gap).setScale(4, ROUND_DOWN);

                if (bidSum.compareTo(NULL) >= 0) {
                    bid.setSum(bidSum);
                }
            } else {
                BigDecimal bidSum = lot.getStartPrice().subtract(gap).setScale(4, ROUND_DOWN);
                if (bidSum.compareTo(NULL) >= 0) {
                    bid.setSum(bidSum);
                }
            }

            save(bid);
            return true;
        }

        return false;
    }

    @Override
    public boolean createBid(Lot lot, User bidder, BigDecimal bidSum) throws ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuctionService auctionService = serviceFactory.getAuctionService();

        Auction auction = auctionService.findByLotId(lot.getId());

        if (auction.getType() == AuctionType.DIRECT) {

            Bid bid = new Bid(bidder.getId(), bidSum, LocalDateTime.now(), auction.getId());

            if (findByAuctionId(auction.getId()).size() > 0) {
                if (isMaxBid(bid) && !isRebid(bid)) {
                    save(bid);
                    return true;
                }
            } else {
                if (bidSum.compareTo(lot.getStartPrice()) > 0) {
                    save(bid);
                    return true;
                }
            }
        }

        return false;
    }
}
