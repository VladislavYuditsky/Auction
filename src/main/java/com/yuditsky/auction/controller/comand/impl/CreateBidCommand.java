package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
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
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.MessageProvider.BID_NOT_CREATED;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.*;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.AUCTION;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class CreateBidCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(CreateBidCommand.class);

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
        try {
            int lotId = Integer.parseInt(request.getParameter(LOT_ID));

            Lot lot = lotService.findById(lotId);

            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute(ID);

            User user = userService.findById(userId);

            BigDecimal bidSum = new BigDecimal(request.getParameter(BID_SUM));

            if (!bidService.createBid(lot, user, bidSum)) {
                redirect(request, response, AUCTION + "?" + LOT_ID + "=" + lotId + "&" +
                        MESSAGE + "=" + BID_NOT_CREATED);
                return;
            }

            redirect(request, response, AUCTION + "?" + LOT_ID + "=" + lotId);
        } catch (ServiceException | NumberFormatException e) {
            logger.error("CreateBidCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
