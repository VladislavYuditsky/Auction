package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.CREATE_AUCTION_PAGE;
import static com.yuditsky.auction.controller.comand.ConstProv.USER_LOTS;

public class CreateAuctionCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String auctionTypeStr = request.getParameter("auctionType");
            String lotIdStr = request.getParameter("lotId");

            ServiceFactory factory = ServiceFactory.getInstance();
            AuctionService auctionService = factory.getAuctionService();
            LotService lotService = factory.getLotService();

            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                try {
                    Lot lot = lotService.findById(lotId);

                    int ownerId = lot.getOwnerId();
                    int currentUserId = (Integer) session.getAttribute("id");

                    if(ownerId == currentUserId) {

                        if(auctionService.findByLotId(lotId) == null) {
                            if (auctionTypeStr != null) {
                                AuctionType auctionType = AuctionType.valueOf(auctionTypeStr.toUpperCase());

                                Auction auction = new Auction(auctionType, lotId, AuctionStatus.WAITING);

                                auctionService.save(auction);

                                //return "userLots";
                                redirect(request, response, USER_LOTS);

                            } else {
                                request.setAttribute("lot", lot);

                                forward(request, response, CREATE_AUCTION_PAGE);
                                //return "createAuction";
                            }
                        } else {
                            //return "userLots";
                            redirect(request, response, USER_LOTS);
                        }
                    } //else 403
                } catch (ServiceException e) {
                    ////
                }
            }
        }

        //return "greeting";
    }
}
