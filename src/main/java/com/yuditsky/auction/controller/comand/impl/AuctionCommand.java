package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.*;

public class AuctionCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(AuctionCommand.class);

    private final LotService lotService;
    private final BidService bidService;
    private final AuctionService auctionService;

    public AuctionCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
        bidService = factory.getBidService();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lotIdStr = request.getParameter("lotId");

        try {
            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                Lot lot = lotService.findById(lotId);

                Auction auction = auctionService.findByLotId(lot.getId());

                if (auction != null) {
                    Bid bid;

                    String page;

                    if (auction.getType() == AuctionType.DIRECT) {
                        bid = bidService.findWithMaxSumByAuctionId(auction.getId());

                        page = DIRECT_AUCTION_PAGE;
                    } else {
                        HttpSession session = request.getSession();
                        int userId = (Integer) session.getAttribute("id");

                        bid = bidService.findMinByBidderIdAndAuctionId(userId, auction.getId());

                        page = REVERS_AUCTION_PAGE;
                    }

                    if (bid != null) {
                        request.setAttribute("bid", bid);
                    } else {
                        request.setAttribute("bid", new Bid());
                    }

                    request.setAttribute("auction", auction);
                    request.setAttribute("lot", lot);

                    forward(request, response, page);
                }
            }
        } catch (ServiceException e) {
            logger.error("AuctionCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
