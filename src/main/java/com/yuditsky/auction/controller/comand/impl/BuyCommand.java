package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.*;
import com.yuditsky.auction.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.yuditsky.auction.controller.comand.ConstProv.REVERS_AUCTION_PAGE;
import static com.yuditsky.auction.controller.comand.ConstProv.USER_LOTS_PAGE;

public class BuyCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                int userId = (Integer) session.getAttribute("id");

                ServiceFactory factory = ServiceFactory.getInstance();
                BidService bidService = factory.getBidService();
                AuctionService auctionService = factory.getAuctionService();
                UserService userService = factory.getUserService();
                LotService lotService = factory.getLotService();
                PaymentService paymentService = factory.getPaymentService();

                try {
                    Auction auction = auctionService.findByLotId(lotId);

                    Bid bid = bidService.findMinByBidderIdAndAuctionId(userId, auction.getId());

                    User user = userService.findById(userId);

                    BigDecimal userBalance = user.getBalance();
                    BigDecimal price;

                    Lot lot = lotService.findById(lotId);

                    if(bid != null) {
                        price = bid.getSum();
                    } else {
                        price = lot.getStartPrice();
                    }

                    if (auction.getStatus() == AuctionStatus.ACTIVE && userBalance.compareTo(price) > 0) {
                        auction.setStatus(AuctionStatus.COMPLETED);
                        auctionService.update(auction);

                        userService.subtractBalance(user, price);

                        User owner = userService.findById(lot.getOwnerId());
                        userService.addBalance(owner, lot.getStartPrice());

                        lot.setOwnerId(userId);
                        lotService.update(lot);

                        Payment payment = new Payment(userId, price, lotId, LocalDateTime.now());
                        paymentService.save(payment);

                        auctionService.delete(auction);

                        //return "userLots";
                        forward(request, response, USER_LOTS_PAGE);
                    } else {
                        //return "reversAuction";
                        forward(request, response, REVERS_AUCTION_PAGE);
                    }
                } catch (ServiceException e) {
                    ///
                }
            }
        }

        //return "greeting";
    }
}
