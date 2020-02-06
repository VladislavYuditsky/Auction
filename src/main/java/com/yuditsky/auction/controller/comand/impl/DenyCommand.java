package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
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

public class DenyCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            UserRole userRole = (UserRole) session.getAttribute("role");

            if (userRole == UserRole.ADMIN) {
                String auctionIdStr = request.getParameter("auctionId");

                if(auctionIdStr != null){
                    int auctionId = Integer.parseInt(auctionIdStr);

                    ServiceFactory factory = ServiceFactory.getInstance();
                    AuctionService auctionService = factory.getAuctionService();

                    try {
                        Auction auction = auctionService.findById(auctionId);

                        auctionService.delete(auction);

                        //return "proposedAuctions";
                        redirect(request, response, PROPOSED_AUCTIONS);
                    } catch (ServiceException e) {
                        /////
                    }
                }
            } //else 403
        }

        //return "greeting";
    }
}
