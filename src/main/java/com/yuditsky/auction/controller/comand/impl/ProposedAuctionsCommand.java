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
import java.util.List;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.PROPOSED_AUCTIONS_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.AUCTIONS;

public class ProposedAuctionsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ProposedAuctionsCommand.class);

    private final AuctionService auctionService;

    public ProposedAuctionsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Auction> auctions = auctionService.findByStatus(AuctionStatus.WAITING);

            request.setAttribute(AUCTIONS, auctions);

            forward(request, response, PROPOSED_AUCTIONS_PAGE);
        } catch (ServiceException e) {
            logger.error("ProposedAuctionsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
