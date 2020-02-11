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
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.Const.NULL;
import static com.yuditsky.auction.Const.REVERS_AUCTION_COEFFICIENT;
import static java.math.BigDecimal.ROUND_DOWN;

public class BidServiceImpl implements BidService {
    private final static Logger logger = LogManager.getLogger(BidServiceImpl.class);

    private final BidDAO bidDAO;
    private final Validator validator;

    public BidServiceImpl() {
        DAOFactory factory = DAOFactory.getInstance();
        bidDAO = factory.getBidDAO();

        validator = new Validator();
    }

    @Override
    public void save(Bid bid) throws ServiceException {
        if(!validator.checkBid(bid)){
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

        if (maxBid.getBidderId() == bid.getBidderId()) {
            return true;
        }

        return false;
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
    public List<Bid> findByBidderId(int id) throws ServiceException {
        try {
            return bidDAO.findByBidderId(id);
        } catch (DAOException e) {
            logger.error("Can't find bids by bidder id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bid findWithMaxSumByAuctionId(int id) throws ServiceException {
        List<Bid> bids = findByAuctionId(id);

        if (bids.size() > 0) {
            Bid maxBid = bids.iterator().next(); //Stream API ///////////////
            for (Bid bid : bids) {
                BigDecimal maxSum = maxBid.getSum();
                BigDecimal curSum = bid.getSum();

                if (maxSum.compareTo(curSum) < 0) {
                    maxBid = bid;
                }
            }

            return maxBid;
        } else {
            return null;
        }
    }

    @Override
    public Bid findMinByBidderIdAndAuctionId(int bidderId, int auctionId) throws ServiceException {
        try {
            List<Bid> bidderBids = bidDAO.findByBidderId(bidderId);
            List<Bid> auctionBids = bidDAO.findByAuctionId(auctionId);

            List<Bid> commonBids = new ArrayList<>();

            for (Bid bid : bidderBids) {
                if (auctionBids.contains(bid)) {
                    commonBids.add(bid);
                }
            }

            if (commonBids.size() > 0) {
                Bid minBid = commonBids.iterator().next();

                for (Bid bid : commonBids) {
                    BigDecimal minSum = minBid.getSum();
                    BigDecimal curSum = bid.getSum();

                    if (minSum.compareTo(curSum) > 0) {
                        minBid = bid;
                    }
                }

                return minBid;
            } else {
                return null;
            }

        } catch (DAOException e) {
            logger.error("Can't find min bid by bidder id and auction id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bid findWithMinSumByAuctionId(int id) throws ServiceException {
        List<Bid> bids = findByAuctionId(id);

        if (bids.size() > 0) {
            Bid minBid = bids.iterator().next();
            for (Bid bid : bids) {
                BigDecimal minSum = minBid.getSum();
                BigDecimal curSum = bid.getSum();

                if (minSum.compareTo(curSum) > 0) {
                    minBid = bid;
                }
            }

            return minBid;
        } else {
            return null;
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
    public boolean showPrice(Lot lot, User bidder) throws ServiceException {
        BigDecimal lotStartPrice = lot.getStartPrice();
        BigDecimal userBalance = bidder.getBalance();

        ServiceFactory factory = ServiceFactory.getInstance();
        AuctionService auctionService = factory.getAuctionService();
        UserService userService = factory.getUserService();

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

                    save(bid);
                } else {
                    save(bid);
                }
            } else {
                BigDecimal bidSum = lot.getStartPrice().subtract(gap).setScale(4, ROUND_DOWN);
                if (bidSum.compareTo(NULL) >= 0) {
                    bid.setSum(bidSum);

                    save(bid);
                } else {
                    save(bid);
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean createBid(Lot lot, User bidder, BigDecimal bidSum) throws ServiceException {

        ServiceFactory factory = ServiceFactory.getInstance();
        AuctionService auctionService = factory.getAuctionService();

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
