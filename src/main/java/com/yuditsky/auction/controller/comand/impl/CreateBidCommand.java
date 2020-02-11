package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.comand.ConstProv.AUCTION;

public class CreateBidCommand extends AbstractCommand {
    private final LotService lotService;
    private final BidService bidService;
    private final UserService userService;

    public CreateBidCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
        bidService = factory.getBidService();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String bidSumStr = request.getParameter("bidSum");
        String lotIdStr = request.getParameter("lotId");

        if (lotIdStr != null) {
            int lotId = Integer.parseInt(lotIdStr);

            try {
                Lot lot = lotService.findById(lotId);

                int userId = (Integer) session.getAttribute("id");

                User user = userService.findById(userId);

                if (bidSumStr != null && !bidSumStr.equals("")) {
                    BigDecimal bidSum = new BigDecimal(bidSumStr);

                    if (!bidService.createBid(lot, user, bidSum)) {
                        //сообщение пользователю
                    }
                }

                //request.setAttribute("lotId", lotId);
                redirect(request, response, AUCTION + "?lotId=" + lotId);
            } catch (ServiceException e) {
                ///
            }
        }
    }
}
