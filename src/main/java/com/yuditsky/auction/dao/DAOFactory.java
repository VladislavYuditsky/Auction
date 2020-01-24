package com.yuditsky.auction.dao;

import com.yuditsky.auction.dao.impl.*;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final AuctionDAO auctionDAOImpl = new SQLAuctionDAO();
    private final UserDAO userDAOImpl = new SQLUserDAO();
    private final BidDAO bidDAOImpl = new SQLBidDAO();
    private final LotDAO lotDAOImpl = new SQLLotDAO();
    private final PaymentDAO paymentDAOImpl = new SQLPaymentDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public AuctionDAO getAuctionDAO() {
        return auctionDAOImpl;
    }

    public UserDAO getUserDAO() {
        return userDAOImpl;
    }

    public BidDAO getBidDAO() {
        return bidDAOImpl;
    }

    public LotDAO getLotDAO() {
        return lotDAOImpl;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAOImpl;
    }
}
