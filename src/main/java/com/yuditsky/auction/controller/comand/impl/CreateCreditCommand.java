package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.CREATE_PAYMENT;

public class CreateCreditCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            ServiceFactory factory = ServiceFactory.getInstance();
            //LotService lotService = factory.getLotService();
            AuctionService auctionService = factory.getAuctionService();
            BidService bidService = factory.getBidService();
            CreditService creditService = factory.getCreditService();

            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                try {
                    Auction auction = auctionService.findByLotId(lotId);

                    if (auction != null) {
                        int winnerId = auction.getWinnerId();

                        int currentUserId = (Integer) session.getAttribute("id");

                        if (winnerId == currentUserId) {
                            Bid bid = bidService.findWithMaxSumByAuctionId(auction.getId());

                            Credit credit = creditService.createCredit(currentUserId, bid.getSum());
                            creditService.save(credit);

                            request.setAttribute("lotId", lotId);

                            redirect(request, response, CREATE_PAYMENT);
                            // newPayment = new NewPayment();///////////////////
                            //newPayment.execute(request);////////////////////////////
                        }//else 403
                    }
                } catch (ServiceException e) {
                    ///
                }
            }

        }

        //return "greeting";
    }
}
