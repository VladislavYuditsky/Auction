package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.yuditsky.auction.controller.comand.ConstProv.PROPOSED_AUCTIONS_PAGE;

public class ProposedAuctionsCommand extends AbstractCommand {
    private final AuctionService auctionService;

    public ProposedAuctionsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Auction> auctions = auctionService.findByStatus(AuctionStatus.WAITING);

            request.setAttribute("auctions", auctions);

            forward(request, response, PROPOSED_AUCTIONS_PAGE);
        } catch (ServiceException e) {
            ////
        }
    }
}
