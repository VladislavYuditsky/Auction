package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.LotDAO;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LotServiceImpl implements LotService {
    private final static Logger logger = LogManager.getLogger(LotServiceImpl.class);

    @Override
    public void save(Lot lot) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        LotDAO lotDAO = factory.getLotDAO();
        try {
            lotDAO.save(lot);
        } catch (DAOException e) {
            logger.error("Can't save lot", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Lot> findByOwnerId(int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        LotDAO lotDAO = factory.getLotDAO();
        try {
            return lotDAO.findByOwnerId(id);
        } catch (DAOException e) {
            logger.error("Can't find lots by owner id = " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Lot> findByBuyerId(int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        LotDAO lotDAO = factory.getLotDAO();
        try {
            return lotDAO.findByBuyerId(id);
        } catch (DAOException e) {
            logger.error("Can't find lots by buyer id = " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Lot findById(int id) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        LotDAO lotDAO = factory.getLotDAO();
        try {
            return lotDAO.findById(id);
        } catch (DAOException e) {
            logger.error("Can't find lot by id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Lot lot) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        LotDAO lotDAO = factory.getLotDAO();
        try {
           lotDAO.update(lot);
        } catch (DAOException e) {
            logger.error("Can't  update lot with id=" + lot.getLotId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Lot lot) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        LotDAO lotDAO = factory.getLotDAO();
        try {
            lotDAO.delete(lot);
        } catch (DAOException e) {
            logger.error("Can't  delete lot with id=" + lot.getLotId(), e);
            throw new ServiceException(e);
        }
    }
}
