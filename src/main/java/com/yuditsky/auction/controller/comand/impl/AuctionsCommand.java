package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.impl.util.PaginationHelper;
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

import static com.yuditsky.auction.controller.comand.impl.util.PaginationHelper.NUMBER_OF_RECORDS_ON_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.AUCTIONS_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.LOTS;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.*;

/**
 * Serves the auctions request.
 * This command available for all users.
 */
public class AuctionsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(AuctionsCommand.class);

    private final LotService lotService;
    private final PaginationHelper paginationHelper;

    public AuctionsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();

        paginationHelper = new PaginationHelper();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strType = request.getParameter(AUCTION_TYPE);
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

        int currentPage;
        try {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } catch (NumberFormatException e) {
            currentPage = 0;
        }

        try {
            List<Lot> lots = lotService.findActiveLotsByAuctionType(type, NUMBER_OF_RECORDS_ON_PAGE,
                    currentPage * NUMBER_OF_RECORDS_ON_PAGE);
            int pagesNumber = paginationHelper.calculatePagesNumber(lotService.findActiveLotsByAuctionType(type),
                    NUMBER_OF_RECORDS_ON_PAGE);

            request.setAttribute(CURRENT_PAGE, currentPage);
            request.setAttribute(PAGES_NUMBER, pagesNumber);
            request.setAttribute(AUCTION_TYPE, strType);
            request.setAttribute(LOTS, lots);
        } catch (ServiceException e) {
            logger.error("AuctionsCommand failed", e);
            forward(request, response, ERROR_PAGE);
            return;
        }

        forward(request, response, AUCTIONS_PAGE);
    }
}
