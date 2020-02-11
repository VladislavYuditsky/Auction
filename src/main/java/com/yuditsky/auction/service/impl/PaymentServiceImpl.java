package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.PaymentDAO;
import com.yuditsky.auction.entity.*;
import com.yuditsky.auction.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final static Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl() {
        DAOFactory factory = DAOFactory.getInstance();
        paymentDAO = factory.getPaymentDAO();
    }

    @Override
    public void save(Payment payment) throws ServiceException {
        try {
            paymentDAO.save(payment);
        } catch (DAOException e) {
            logger.error("Can't save payment", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Payment> findByPayerId(int id) throws ServiceException {
        try {
            return paymentDAO.findByPayerId(id);
        } catch (DAOException e) {
            logger.error("Can't find payments by payer id = " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean createPayment(Lot lot, User payer) throws ServiceException {
        ServiceFactory factory = ServiceFactory.getInstance();
        AuctionService auctionService = factory.getAuctionService();
        UserService userService = factory.getUserService();
        BidService bidService = factory.getBidService();
        PaymentService paymentService = factory.getPaymentService();
        LotService lotService = factory.getLotService();

        Auction auction = auctionService.findByLotId(lot.getId());

        if (auction.getStatus() == AuctionStatus.COMPLETED) {

            Bid bid;
            if (auction.getType() == AuctionType.DIRECT) {
                bid = bidService.findWithMaxSumByAuctionId(auction.getId());
            } else {
                bid = bidService.findWithMinSumByAuctionId(auction.getId());
            }

            BigDecimal lotPrice = bid.getSum();
            BigDecimal userBalance = payer.getBalance();

            if (userBalance.compareTo(lotPrice) > 0) {
                userService.subtractBalance(payer, lotPrice);

                User owner = userService.findById(lot.getOwnerId());
                userService.addBalance(owner, lotPrice);

                lot.setOwnerId(payer.getId());
                lotService.update(lot);

                Payment payment = new Payment(payer.getId(), lotPrice, lot.getId(), LocalDateTime.now());
                paymentService.save(payment);

                auctionService.delete(auction);

                return true;
            }
        }

        return false;
    }
}
