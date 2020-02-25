package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
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

import static com.yuditsky.auction.controller.provider.JspPageProvider.AWAITING_PAYMENT_LOTS_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.LOTS;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class AwaitingPaymentLotsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(AwaitingPaymentLotsCommand.class);

    private final LotService lotService;

    public AwaitingPaymentLotsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute(ID);

            List<Lot> lots = lotService.findAwaitingPaymentLots(id);

            request.setAttribute(LOTS, lots);

            forward(request, response, AWAITING_PAYMENT_LOTS_PAGE);
        } catch (ServiceException e) {
            logger.error("AwaitingPaymentLotsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
