package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.impl.util.PaginationHelper;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.service.AuctionService;
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
import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.PROPOSED_AUCTIONS_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.AUCTIONS;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.CURRENT_PAGE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.PAGES_NUMBER;

public class ProposedAuctionsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ProposedAuctionsCommand.class);

    private final AuctionService auctionService;
    private final PaginationHelper paginationHelper;

    public ProposedAuctionsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();

        paginationHelper = new PaginationHelper();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage;
        try {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } catch (NumberFormatException e) {
            currentPage = 0;
        }

        try {
            List<Auction> auctions = auctionService.findByStatus(AuctionStatus.WAITING, NUMBER_OF_RECORDS_ON_PAGE,
                    currentPage * NUMBER_OF_RECORDS_ON_PAGE);

            int pagesNumber = paginationHelper.calculatePagesNumber(auctionService.findByStatus(AuctionStatus.WAITING),
                    NUMBER_OF_RECORDS_ON_PAGE);

            request.setAttribute(AUCTIONS, auctions);
            request.setAttribute(CURRENT_PAGE, currentPage);
            request.setAttribute(PAGES_NUMBER, pagesNumber);

            forward(request, response, PROPOSED_AUCTIONS_PAGE);
        } catch (ServiceException e) {
            logger.error("ProposedAuctionsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
