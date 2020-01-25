package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.BidDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.service.BidService;
import com.yuditsky.auction.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BidServiceImpl implements BidService {
    private final static Logger logger = LogManager.getLogger(BidServiceImpl.class);

    @Override
    public void save(Bid bid) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        BidDAO bidDAO = factory.getBidDAO();
        try {
            bidDAO.save(bid);
        } catch (DAOException e) {
            logger.error("Can't save bid", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bid> findByAuctionId(int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        BidDAO bidDAO = factory.getBidDAO();
        try {
            return bidDAO.findByAuctionId(id);
        } catch (DAOException e) {
            logger.error("Can't find bids by auction id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bid> findByBidderId(int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        BidDAO bidDAO = factory.getBidDAO();
        try {
            return bidDAO.findByBidderId(id);
        } catch (DAOException e) {
            logger.error("Can't find bids by bidder id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Bid bid) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        BidDAO bidDAO = factory.getBidDAO();
        try {
            bidDAO.delete(bid);
        } catch (DAOException e) {
            logger.error("Can't delete bid " + bid.getBidId(), e);
            throw new ServiceException(e);
        }
    }
}
