package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.*;
import com.yuditsky.auction.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.yuditsky.auction.controller.comand.ConstProv.AWAITING_PAYMENT_LOTS;

public class CreatePaymentCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            if(lotIdStr != null){
                int lotId = Integer.parseInt(lotIdStr);

                ServiceFactory factory = ServiceFactory.getInstance();
                AuctionService auctionService = factory.getAuctionService();
                UserService userService = factory.getUserService();
                BidService bidService = factory.getBidService();
                PaymentService paymentService = factory.getPaymentService();
                LotService lotService = factory.getLotService();

                try {
                    Auction auction = auctionService.findByLotId(lotId);

                    int currentUserId = (Integer)session.getAttribute("id");

                    if(auction.getStatus() == AuctionStatus.COMPLETED && currentUserId == auction.getWinnerId()){

                        Bid bid = bidService.findWithMaxSumByAuctionId(auction.getId());
                        BigDecimal lotPrice = bid.getSum();

                        User user = userService.findById(currentUserId);
                        BigDecimal userBalance = user.getBalance();

                        if(userBalance.compareTo(lotPrice) > 0){
                            userService.subtractBalance(user, lotPrice);

                            Lot lot = lotService.findById(lotId);

                            User owner = userService.findById(lot.getOwnerId());
                            userService.addBalance(owner, lotPrice);

                            lot.setOwnerId(currentUserId);
                            lotService.update(lot);

                            Payment payment = new Payment(currentUserId, lotPrice, lotId, LocalDateTime.now());
                            paymentService.save(payment);

                            auctionService.delete(auction);

                            //return "awaitingPaymentLots";
                        } else{
                            //////////////////обработать
                            //return "awaitingPaymentLots";

                        }

                        redirect(request, response, AWAITING_PAYMENT_LOTS);
                    }
                } catch (ServiceException e) {
                    ///
                }


            }
        }

        //return "greeting";
    }
}
