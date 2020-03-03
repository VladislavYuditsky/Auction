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
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.JspPageProvider.DIRECT_AUCTION_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.BID;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.LOT_ID;

/**
 * Serves the direct_auction request.
 * This command available only for authorized users.
 */
public class DirectAuctionCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(DirectAuctionCommand.class);

    private final BidService bidService;
    private final AuctionService auctionService;

    public DirectAuctionCommand() {
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
                Bid bid = bidService.findWithMaxSumByAuctionId(auction.getId());

                if (bid != null) {
                    request.setAttribute(BID, bid);
                } else {
                    request.setAttribute(BID, new Bid());
                }

                forward(request, response, DIRECT_AUCTION_PAGE);
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("DirectAuctionCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
