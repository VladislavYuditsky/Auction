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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            UserRole role = (UserRole) session.getAttribute("role");

            if (role == UserRole.ADMIN) {
                ServiceFactory factory = ServiceFactory.getInstance();
                AuctionService auctionService = factory.getAuctionService();

                try {
                    List<Auction> auctions = auctionService.findByStatus(AuctionStatus.WAITING);

                    request.setAttribute("auctions", auctions);

                    //return "proposedAuctions";
                    forward(request, response, PROPOSED_AUCTIONS_PAGE);
                } catch (ServiceException e) {
                    ////
                }

            } else {
                ///403
            }
        }

        //return "signIn";
    }
}
