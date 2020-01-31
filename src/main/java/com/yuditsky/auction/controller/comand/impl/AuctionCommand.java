package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String bidSumStr = request.getParameter("bidSum");
            String lotIdStr = request.getParameter("lotId");

            ServiceFactory factory = ServiceFactory.getInstance();
            LotService lotService = factory.getLotService();
            BidService bidService = factory.getBidService();
            AuctionService auctionService = factory.getAuctionService();

            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                try {
                    Lot lot = lotService.findById(lotId);

                    Auction auction = auctionService.findByLotId(lot.getId());

                    if (bidSumStr != null) {
                        BigDecimal bidSum = new BigDecimal(bidSumStr);
                        int userId = (Integer) session.getAttribute("id");

                        Bid bid = new Bid(userId, bidSum, LocalDateTime.now(), auction.getId());
                        try {
                            if(bidService.isMaxBid(bid, auction.getId())){
                                bidService.save(bid);
                            }
                        } catch (ServiceException e) {
                            ///
                        }
                    }


                    Bid bid = bidService.findWithMaxSumByAuctionId(auction.getId());

                    request.setAttribute("lot", lot);
                    request.setAttribute("maxBid", bid);

                    return "auction";
                } catch (ServiceException e) {
                    ////
                }
            }
        }

        return "signIn";
    }
}
