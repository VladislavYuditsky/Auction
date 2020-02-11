package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.AUCTION;

public class ShowPriceCommand extends AbstractCommand {
    private final LotService lotService;
    private final BidService bidService;
    private final AuctionService auctionService;
    private final UserService userService;

    public ShowPriceCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
        bidService = factory.getBidService();
        auctionService = factory.getAuctionService();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lotIdStr = request.getParameter("lotId");

        if (lotIdStr != null) {
            int lotId = Integer.parseInt(lotIdStr);

            try {
                Lot lot = lotService.findById(lotId);

                HttpSession session = request.getSession();
                int userId = (Integer) session.getAttribute("id");
                User user = userService.findById(userId);

                if (bidService.showPrice(lot, user)) {
                    //сообщения юзеру
                }

                redirect(request, response, AUCTION + "?lotId=" + lotId);
            } catch (ServiceException e) {
                ////
            }
        }
    }
}
