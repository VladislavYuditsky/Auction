package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.*;

public class ChangeAuctionStatusCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String page = GREETING_PAGE;

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

                            page = PROPOSED_AUCTIONS;
                        }

                        if(status == AuctionStatus.ACTIVE){
                            auctionService.finishAuction(auction);

                            page = AUCTIONS;
                        }
                    } catch (ServiceException e) {
                        ///
                    }
                }
            }
        }

        //return  page;
        redirect(request, response, page);
    }
}
