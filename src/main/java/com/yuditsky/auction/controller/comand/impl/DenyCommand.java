package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.PROPOSED_AUCTIONS;

public class DenyCommand extends AbstractCommand { //DeleteAuction ////////////////////////////////////
    private final AuctionService auctionService;

    public DenyCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String auctionIdStr = request.getParameter("auctionId");

        if (auctionIdStr != null) { //Optional
            int auctionId = Integer.parseInt(auctionIdStr);

            try {
                Auction auction = auctionService.findById(auctionId);

                auctionService.delete(auction);

                redirect(request, response, PROPOSED_AUCTIONS);
            } catch (ServiceException e) {
                /////
            }
        } //else 403
    }
}
