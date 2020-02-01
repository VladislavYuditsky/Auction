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

import static com.yuditsky.auction.Const.NULL;
import static com.yuditsky.auction.Const.REVERS_AUCTION_COEFFICIENT;
import static java.math.BigDecimal.ROUND_DOWN;

public class AddNewBid implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            ServiceFactory factory = ServiceFactory.getInstance();
            LotService lotService = factory.getLotService();
            BidService bidService = factory.getBidService();
            AuctionService auctionService = factory.getAuctionService();

            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                try {
                    Auction auction = auctionService.findByLotId(lotId);

                    int userId = (Integer) session.getAttribute("id");

                    Bid minBid = bidService.findWithMinSumByAuctionId(auction.getId());

                    Lot lot = lotService.findById(lotId);

                    BigDecimal gap = lot.getStartPrice().multiply(REVERS_AUCTION_COEFFICIENT); //вынести в утил мб

                    Bid bid = new Bid(userId, NULL, LocalDateTime.now(), auction.getId());

                    if (minBid != null) {
                        BigDecimal bidSum = minBid.getSum().subtract(gap).setScale(4, ROUND_DOWN);

                        if (bidSum.compareTo(NULL) >= 0) {
                            bid.setSum(bidSum);

                            bidService.save(bid);
                        } else {
                            bidService.save(bid);
                        }
                    } else {
                        BigDecimal bidSum = lot.getStartPrice().subtract(gap).setScale(4, ROUND_DOWN);
                        if (bidSum.compareTo(NULL) >= 0) {
                           bid.setSum(bidSum);

                            bidService.save(bid);
                        } else {
                            bidService.save(bid);
                        }
                    }

                    request.setAttribute("lot", lot);
                    request.setAttribute("bid", bid);

                    return "reversAuction";
                } catch (ServiceException e) {
                    ////
                }
            }
        }

        return "signIn";
    }
}
