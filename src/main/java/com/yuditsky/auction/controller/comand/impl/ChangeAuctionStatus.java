package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeAuctionStatus implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String page = "signIn";

        if (session.getAttribute("id") != null) {
            UserRole role = (UserRole) session.getAttribute("role");

            if (role == UserRole.ADMIN) {
                String auctionIdStr = request.getParameter("auctionId");

                if(auctionIdStr != null){
                    int auctionId = Integer.parseInt(auctionIdStr);

                    ServiceFactory factory = ServiceFactory.getInstance();
                    AuctionService auctionService = factory.getAuctionService();

                    try {
                        Auction auction = auctionService.findById(auctionId);

                        AuctionStatus status = auction.getStatus();

                        if(status == AuctionStatus.WAITING){
                            auction.setStatus(AuctionStatus.ACTIVE);
                            auctionService.update(auction);

                            page = "proposedAuctions";
                        }

                        if(status == AuctionStatus.ACTIVE){
                            auctionService.finishAuction(auction);

                            page = "main";
                        }
                    } catch (ServiceException e) {
                        ///
                    }
                }
            }
        }

        return  page;
    }
}
