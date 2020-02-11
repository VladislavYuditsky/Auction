package com.yuditsky.auction.service;

import com.yuditsky.auction.service.impl.*;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    private UserService userServiceImpl = new UserServiceImpl();
    private AuctionService auctionServiceImpl = new AuctionServiceImpl();
    private BidService bidServiceImpl = new BidServiceImpl();
    private CreditService creditServiceImpl = new CreditServiceImpl();
    private LotService lotServiceImpl = new LotServiceImpl();
    private PaymentService paymentServiceImpl = new PaymentServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userServiceImpl;
    }

    public AuctionService getAuctionService() {
        return auctionServiceImpl;
    }

    public BidService getBidService() {
        return bidServiceImpl;
    }

    public CreditService getCreditService() {
        return creditServiceImpl;
    }

    public LotService getLotService() {
        return lotServiceImpl;
    }

    public PaymentService getPaymentService() {
        return paymentServiceImpl;
    }
}
