package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.impl.util.PaginationHelper;
import com.yuditsky.auction.entity.Lot;
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
import java.util.List;

import static com.yuditsky.auction.controller.comand.impl.util.PaginationHelper.NUMBER_OF_RECORDS_ON_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.AWAITING_PAYMENT_LOTS_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.LOTS;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.CURRENT_PAGE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.PAGES_NUMBER;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class AwaitingPaymentLotsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(AwaitingPaymentLotsCommand.class);

    private final LotService lotService;
    private final PaginationHelper paginationHelper;

    public AwaitingPaymentLotsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();

        paginationHelper = new PaginationHelper();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute(ID);

        int currentPage;
        try {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } catch (NumberFormatException e) {
            currentPage = 0;
        }

        try {
            List<Lot> lots = lotService.findAwaitingPaymentLots(id, NUMBER_OF_RECORDS_ON_PAGE,
                    currentPage * NUMBER_OF_RECORDS_ON_PAGE);
            int pagesNumber = paginationHelper.calculatePagesNumber(lotService.findAwaitingPaymentLots(id),
                    NUMBER_OF_RECORDS_ON_PAGE);

            request.setAttribute(LOTS, lots);
            request.setAttribute(CURRENT_PAGE, currentPage);
            request.setAttribute(PAGES_NUMBER, pagesNumber);

            forward(request, response, AWAITING_PAYMENT_LOTS_PAGE);
        } catch (ServiceException e) {
            logger.error("AwaitingPaymentLotsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
