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
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.*;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.LOT_ID;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.DIRECT_AUCTION;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.REVERS_AUCTION;


public class AuctionCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(AuctionCommand.class);

    private final LotService lotService;
    private final AuctionService auctionService;

    public AuctionCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int lotId = Integer.parseInt(request.getParameter(LOT_ID));

            Lot lot = lotService.findById(lotId);
            Auction auction = auctionService.findByLotId(lotId);

            if (auction != null) {
                request.setAttribute(AUCTION, auction);
                request.setAttribute(LOT, lot);
                request.setAttribute(MESSAGE, request.getParameter(MESSAGE));

                if (auction.getType() == AuctionType.DIRECT) {
                    forward(request, response, DIRECT_AUCTION);
                } else {
                    forward(request, response, REVERS_AUCTION);
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("AuctionCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
