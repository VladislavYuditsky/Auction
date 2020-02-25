package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.MessageProvider.PAYMENT_NOT_CREATED;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.LOT_ID;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.AUCTION;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.AWAITING_PAYMENT_LOTS;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class CreatePaymentCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(CreatePaymentCommand.class);

    private final AuctionService auctionService;
    private final UserService userService;
    private final PaymentService paymentService;
    private LotService lotService;

    public CreatePaymentCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();
        userService = factory.getUserService();
        paymentService = factory.getPaymentService();
        lotService = factory.getLotService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int lotId = Integer.parseInt(request.getParameter(LOT_ID));
            Auction auction = auctionService.findByLotId(lotId);

            HttpSession session = request.getSession();
            int currentUserId = (int) session.getAttribute(ID);

            if (currentUserId == auction.getWinnerId()) {
                User user = userService.findById(currentUserId);
                Lot lot = lotService.findById(lotId);

                if (!paymentService.createPayment(lot, user)) {
                    redirect(request, response, AUCTION + "?" + LOT_ID + "=" + lotId + "&" +
                            MESSAGE + "=" + PAYMENT_NOT_CREATED);
                    return;
                }
            }

            redirect(request, response, AWAITING_PAYMENT_LOTS);
        } catch (ServiceException | NumberFormatException e) {
            logger.error("CreatePaymentCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
