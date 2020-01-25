package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.AuctionDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class AuctionServiceImpl implements AuctionService {
    private final static Logger logger = LogManager.getLogger(AuctionServiceImpl.class);

    @Override
    public void save(Auction auction) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        AuctionDAO auctionDAO = factory.getAuctionDAO();
        try {
            auctionDAO.save(auction);
        } catch (DAOException e) {
            logger.error("Can't save auction", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Auction findById(int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        AuctionDAO auctionDAO = factory.getAuctionDAO();
        try {
            return auctionDAO.findById(id);
        } catch (DAOException e) {
            logger.error("Can't find auction with id " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Auction> findByType(AuctionType type) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        AuctionDAO auctionDAO = factory.getAuctionDAO();
        try {
            return auctionDAO.findByType(type);
        } catch (DAOException e) {
            logger.error("Can't find auctions by type", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateFinishTime(Auction auction, LocalDateTime finishTime) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        AuctionDAO auctionDAO = factory.getAuctionDAO();
        try{
            auctionDAO.updateFinishTime(auction, finishTime);
        } catch (DAOException e) {
            logger.error("Can't update finish time for auction " + auction.getAuctionId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Auction auction) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        AuctionDAO auctionDAO = factory.getAuctionDAO();
        try{
            auctionDAO.delete(auction);
        } catch (DAOException e) {
            logger.error("Can't delete auction " + auction.getAuctionId(), e);
            throw new ServiceException(e);
        }
    }
}
