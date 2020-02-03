package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class NewCredit implements Command {
    @Override
    public String execute(HttpServletRequest request) {
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

                            NewPayment newPayment = new NewPayment();///////////////////
                            newPayment.execute(request);////////////////////////////
                        }//else 403
                    }
                } catch (ServiceException e) {
                    ///
                }
            }

        }

        return "greeting";
    }
}
