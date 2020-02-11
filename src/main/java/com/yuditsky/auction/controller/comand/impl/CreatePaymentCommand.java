package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.AWAITING_PAYMENT_LOTS;

public class CreatePaymentCommand extends AbstractCommand {
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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lotIdStr = request.getParameter("lotId");
        if (lotIdStr != null) {
            int lotId = Integer.parseInt(lotIdStr);

            try {
                Auction auction = auctionService.findByLotId(lotId);

                HttpSession session = request.getSession();
                int currentUserId = (Integer) session.getAttribute("id");

                if (currentUserId == auction.getWinnerId()) {
                    User user = userService.findById(currentUserId);
                    Lot lot = lotService.findById(lotId);

                    if (!paymentService.createPayment(lot, user)) {
                        //сообщение пользователю
                    }
                }
            } catch (ServiceException e) {
                ///
            }

            redirect(request, response, AWAITING_PAYMENT_LOTS);
        }
    }
}
