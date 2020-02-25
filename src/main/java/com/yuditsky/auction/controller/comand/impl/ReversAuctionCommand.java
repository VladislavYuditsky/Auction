package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.BidService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.REVERS_AUCTION_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.BID;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.LOT_ID;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class ReversAuctionCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ReversAuctionCommand.class);

    private final BidService bidService;
    private final AuctionService auctionService;

    public ReversAuctionCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        bidService = factory.getBidService();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int lotId = Integer.parseInt(request.getParameter(LOT_ID));

            Auction auction = auctionService.findByLotId(lotId);

            if (auction != null) {
                HttpSession session = request.getSession();
                int userId = (int) session.getAttribute(ID);

                Bid bid = bidService.findMinByBidderIdAndAuctionId(userId, auction.getId());

                if (bid != null) {
                    request.setAttribute(BID, bid);
                } else {
                    request.setAttribute(BID, new Bid());
                }

                forward(request, response, REVERS_AUCTION_PAGE);
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("ReversAuctionCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
