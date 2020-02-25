package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
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

import static com.yuditsky.auction.controller.provider.JspPageProvider.CREATE_AUCTION_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.MessageProvider.AUCTION_NOT_CREATED;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.LOT;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.AUCTION_TYPE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.LOT_ID;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.USER_LOTS;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

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
        try {
            int lotId = Integer.parseInt(request.getParameter(LOT_ID));

            Lot lot = lotService.findById(lotId);
            Auction auction = auctionService.findByLotId(lotId);

            if (auction == null && lot != null) {
                int ownerId = lot.getOwnerId();

                HttpSession session = request.getSession();
                int currentUserId = (Integer) session.getAttribute(ID);

                if (ownerId == currentUserId) {
                    String auctionTypeStr = request.getParameter(AUCTION_TYPE);

                    if (auctionTypeStr != null) {
                        AuctionType type = AuctionType.valueOf(auctionTypeStr.toUpperCase());

                        if (auctionService.createAuction(lotId, type)) {
                            redirect(request, response, USER_LOTS);
                        }
                    } else {
                        request.setAttribute(LOT, lot);
                        forward(request, response, CREATE_AUCTION_PAGE);
                    }
                }
            } else {
                request.setAttribute(MESSAGE, AUCTION_NOT_CREATED);
                request.setAttribute(LOT_ID, lotId);

                forward(request, response, USER_LOTS);
            }
        } catch (ServiceException e) {
            logger.error("CreateAuctionCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
