package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.yuditsky.auction.controller.comand.ConstProv.DIRECT_AUCTION;

public class CreateBidCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                    }
                } catch (ServiceException e) {
                    ///
                }
            }
        }

        redirect(request, response, DIRECT_AUCTION);
    }
}
