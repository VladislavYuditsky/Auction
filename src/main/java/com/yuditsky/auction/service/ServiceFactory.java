package com.yuditsky.auction.service;

import com.yuditsky.auction.service.impl.*;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService userServiceImpl = new UserServiceImpl();
    private final AuctionService auctionServiceImpl = new AuctionServiceImpl();
    private final BidService bidServiceImpl = new BidServiceImpl();
    private final CreditService creditServiceImpl = new CreditServiceImpl();
    private final LotService lotServiceImpl = new LotServiceImpl();
    private final PaymentService paymentServiceImpl = new PaymentServiceImpl();

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
