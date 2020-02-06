package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.REVERS_AUCTION;

public class ShowPriceCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            ServiceFactory factory = ServiceFactory.getInstance();
            LotService lotService = factory.getLotService();
            BidService bidService = factory.getBidService();
            AuctionService auctionService = factory.getAuctionService();
            UserService userService = factory.getUserService();

            if (lotIdStr != null) {
                int lotId = Integer.parseInt(lotIdStr);

                try {
                    Lot lot = lotService.findById(lotId);

                    int userId = (Integer) session.getAttribute("id");
                    User user = userService.findById(userId);

                    if(bidService.showPrice(lot, user)){
                        //сообщения юзеру
                    }

                    redirect(request, response, REVERS_AUCTION);
                } catch (ServiceException e) {
                    ////
                }
            }
        }
    }
}
