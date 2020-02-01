package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
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
            UserService userService = factory.getUserService();

            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                try {
                    Lot lot = lotService.findById(lotId);

                    Auction auction = auctionService.findByLotId(lot.getId());

                    int userId = (Integer) session.getAttribute("id");

                    if (auction.getType() == AuctionType.DIRECT) {

                        if (bidSumStr != null) {
                            BigDecimal bidSum = new BigDecimal(bidSumStr);

                            Bid bid = new Bid(userId, bidSum, LocalDateTime.now(), auction.getId());

                            if (bidService.findByAuctionId(auction.getId()).size() > 0) {
                                if (bidService.isMaxBid(bid) && !bidService.isRebid(bid)) {
                                    bidService.save(bid);
                                }
                            } else {
                                if (bidSum.compareTo(lot.getStartPrice()) > 0) {
                                    bidService.save(bid);
                                }
                            }
                        }

                        Bid bid = bidService.findWithMaxSumByAuctionId(auction.getId());

                        if (bid != null) {
                            request.setAttribute("maxBid", bid);
                        } else {
                            request.setAttribute("maxBid", new Bid());
                        }

                        request.setAttribute("auction", auction);
                        request.setAttribute("lot", lot);

                        return "directAuction";
                    } else {
                        Bid bid = bidService.findMinByBidderIdAndAuctionId(userId, auction.getId());

                        if (bid != null) {
                            request.setAttribute("bid", bid);
                        } else {
                            request.setAttribute("bid", new Bid());
                        }
                        request.setAttribute("lot", lot);
                        return "reversAuction";
                    }
                } catch (ServiceException e) {
                    ////
                }
            }
        }

        return "signIn";
    }
}
