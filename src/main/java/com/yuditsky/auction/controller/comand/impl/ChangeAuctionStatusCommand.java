package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.AUCTION_ID;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.AUCTIONS;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.PROPOSED_AUCTIONS;

/**
 * Serves the change_auction_status request.
 * This command available only for admin.
 */
public class ChangeAuctionStatusCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ChangeAuctionStatusCommand.class);

    private final AuctionService auctionService;

    public ChangeAuctionStatusCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int auctionId = Integer.parseInt(request.getParameter(AUCTION_ID));

            Auction auction = auctionService.findById(auctionId);

            auctionService.changeStatus(auction);

            auction = auctionService.findById(auction.getId());

            if (auction != null && auction.getStatus() == AuctionStatus.ACTIVE) {
                redirect(request, response, PROPOSED_AUCTIONS);
            } else {
                redirect(request, response, AUCTIONS);
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("ChangeAuctionStatusCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
