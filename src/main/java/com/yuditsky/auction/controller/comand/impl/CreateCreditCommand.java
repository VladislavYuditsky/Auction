package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.CREATE_PAYMENT;

public class CreateCreditCommand extends AbstractCommand {
    private final AuctionService auctionService;
    private final BidService bidService;
    private final CreditService creditService;
    private final UserService userService;

    public CreateCreditCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();
        bidService = factory.getBidService();
        creditService = factory.getCreditService();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String lotIdStr = request.getParameter("lotId");

        if (lotIdStr != null) {
            int lotId = Integer.parseInt(lotIdStr);

            try {
                Auction auction = auctionService.findByLotId(lotId);

                if (auction != null) {
                    int winnerId = auction.getWinnerId();

                    int currentUserId = (Integer) session.getAttribute("id");

                    if (winnerId == currentUserId) {

                        Bid bid;
                        if (auction.getType() == AuctionType.DIRECT) {
                            bid = bidService.findWithMaxSumByAuctionId(auction.getId());
                        } else {
                            bid = bidService.findWithMinSumByAuctionId(auction.getId());
                        }

                        creditService.createCredit(currentUserId, bid.getSum());

                        User user = userService.findById(winnerId);
                        userService.addBalance(user, bid.getSum());

                        redirect(request, response, CREATE_PAYMENT + "?lotId=" + lotId);
                    }//else 403
                }
            } catch (ServiceException e) {
                ///
            }
        }
    }
}
