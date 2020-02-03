package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CreateAuction implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String auctionTypeStr = request.getParameter("auctionType");
            String lotIdStr = request.getParameter("lotId");

            ServiceFactory factory = ServiceFactory.getInstance();
            AuctionService auctionService = factory.getAuctionService();
            LotService lotService = factory.getLotService();

            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                if (auctionTypeStr != null) {
                    AuctionType auctionType = AuctionType.valueOf(auctionTypeStr.toUpperCase());

                    Auction auction = new Auction(auctionType, lotId, AuctionStatus.WAITING);

                    try {
                        auctionService.save(auction);

                        return "userLots";
                    } catch (ServiceException e) {
                        //
                    }

                } else {
                    try {
                        Lot lot = lotService.findById(lotId);

                        request.setAttribute("lot", lot);

                        return "createAuction";
                    } catch (ServiceException e) {
                        ///
                    }
                }
            }
        }

        return "greeting";
    }
}
