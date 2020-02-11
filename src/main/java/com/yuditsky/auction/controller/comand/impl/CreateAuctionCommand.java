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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.CREATE_AUCTION_PAGE;
import static com.yuditsky.auction.controller.comand.ConstProv.USER_LOTS;

public class CreateAuctionCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(CreateAuctionCommand.class);

    private final AuctionService auctionService;
    private final LotService lotService;

    public CreateAuctionCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();
        lotService = factory.getLotService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String auctionTypeStr = request.getParameter("auctionType");
        String lotIdStr = request.getParameter("lotId");

        if (lotIdStr != null) {
            int lotId = Integer.parseInt(lotIdStr);

            try {
                Lot lot = lotService.findById(lotId); ///mb null

                Auction auction = auctionService.findByLotId(lot.getId());

                if(auction == null) {
                    int ownerId = lot.getOwnerId();
                    int currentUserId = (Integer) session.getAttribute("id");

                    if (ownerId == currentUserId) {
                        if (auctionTypeStr != null) {
                            AuctionType type = AuctionType.valueOf(auctionTypeStr.toUpperCase());

                            if (auctionService.createAuction(lotId, type)) {
                                redirect(request, response, USER_LOTS);
                            }
                        } else {
                            request.setAttribute("lot", lot);

                            forward(request, response, CREATE_AUCTION_PAGE);
                        }
                    } //else 403
                } else {
                    redirect(request, response, USER_LOTS);
                }
            } catch (ServiceException e) {
                ////
            }
        }
    }
}
