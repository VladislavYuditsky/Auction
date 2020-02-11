package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.yuditsky.auction.controller.comand.ConstProv.AUCTIONS_PAGE;
import static com.yuditsky.auction.controller.comand.ConstProv.ERROR_PAGE;

public class AuctionsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(AuctionsCommand.class);

    private final LotService lotService;

    public AuctionsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strType = request.getParameter("auctionType");
        AuctionType type;

        if (strType == null) {
            type = AuctionType.DIRECT;
        } else {
            try {
                type = AuctionType.valueOf(strType.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error("Illegal auction type.", e);
                type = AuctionType.DIRECT;
            }
        }

        try {
            List<Lot> lots = lotService.findActiveLotsByAuctionType(type);

            request.setAttribute("lots", lots);
        } catch (ServiceException e) {
            logger.error("AuctionsCommand failed", e);
            forward(request, response, ERROR_PAGE);
            return;
        }

        forward(request, response, AUCTIONS_PAGE);
    }
}
