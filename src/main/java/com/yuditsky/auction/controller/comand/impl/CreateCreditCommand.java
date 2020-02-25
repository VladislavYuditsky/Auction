package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Bid;
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
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.LOT_ID;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.CREATE_PAYMENT;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class CreateCreditCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(CreateCreditCommand.class);

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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int lotId = Integer.parseInt(request.getParameter(LOT_ID));
            Auction auction = auctionService.findByLotId(lotId);

            if (auction != null) {
                int winnerId = auction.getWinnerId();

                HttpSession session = request.getSession();
                int currentUserId = (Integer) session.getAttribute(ID);

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

                    redirect(request, response, CREATE_PAYMENT + "?" + LOT_ID + "=" + lotId);
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("CreateCreditCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
