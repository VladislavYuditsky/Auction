package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.AuctionDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.BidService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AuctionServiceImpl implements AuctionService {
    private final static Logger logger = LogManager.getLogger(AuctionServiceImpl.class);

    private final AuctionDAO auctionDAO;

    public AuctionServiceImpl() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        auctionDAO = daoFactory.getAuctionDAO();
    }

    @Override
    public void save(Auction auction) throws ServiceException {
        try {
            auctionDAO.save(auction);
        } catch (DAOException e) {
            logger.error("Can't save auction", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Auction findById(int id) throws ServiceException {
        try {
            return auctionDAO.findById(id);
        } catch (DAOException e) {
            logger.error("Can't find auction with id " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Auction> findByType(AuctionType type) throws ServiceException {
        try {
            return auctionDAO.findByType(type);
        } catch (DAOException e) {
            logger.error("Can't find auctions by type", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Auction> findByStatus(AuctionStatus status) throws ServiceException {
        try {
            return auctionDAO.findByStatus(status);
        } catch (DAOException e) {
            logger.error("Can't find auctions by status", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Auction> findByWinnerId(int id) throws ServiceException {
        try {
            return auctionDAO.findByWinnerId(id);
        } catch (DAOException e) {
            logger.error("Can't find auctions by winner id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Auction findByLotId(int id) throws ServiceException {
        try {
            return auctionDAO.findByLotId(id);
        } catch (DAOException e) {
            logger.error("Can't find auction by lotId=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Auction auction) throws ServiceException {
        try {
            auctionDAO.update(auction);
        } catch (DAOException e) {
            logger.error("Can't update auction with id=" + auction.getId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Auction auction) throws ServiceException {
        try {
            auctionDAO.delete(auction);
        } catch (DAOException e) {
            logger.error("Can't delete auction " + auction.getId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void finishAuction(Auction auction) throws ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        BidService bidService = serviceFactory.getBidService();

        if (auction.getType() == AuctionType.DIRECT) {
            Bid bid = bidService.findWithMaxSumByAuctionId(auction.getId());

            if (bid != null) {
                int winnerId = bid.getBidderId();

                auction.setWinnerId(winnerId);
                auction.setStatus(AuctionStatus.COMPLETED);

                update(auction);
            } else {
                delete(auction);
            }
        } else {
            delete(auction);
        }
    }

    @Override
    public void changeStatus(Auction auction) throws ServiceException {
        AuctionStatus status = auction.getStatus();

        if (status == AuctionStatus.WAITING) {
            auction.setStatus(AuctionStatus.ACTIVE);
            update(auction);
        }

        if (status == AuctionStatus.ACTIVE) {
            finishAuction(auction);
        }
    }

    @Override
    public boolean createAuction(int lotId, AuctionType type) throws ServiceException {
        if (findByLotId(lotId) == null) {
            Auction auction = new Auction(type, lotId, AuctionStatus.WAITING);

            save(auction);
            return true;
        } else {
            return false;
        }
    }
}
