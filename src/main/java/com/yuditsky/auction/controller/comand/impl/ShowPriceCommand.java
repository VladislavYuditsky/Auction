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

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.MessageProvider.INSUFFICIENT_FUNDS;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.LOT_ID;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.AUCTION;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ID;

/**
 * Serves the show_price request.
 * This command available only for authorized users.
 */
public class ShowPriceCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ShowPriceCommand.class);

    private final LotService lotService;
    private final BidService bidService;
    private final UserService userService;

    public ShowPriceCommand() {
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
            int userId = (Integer) session.getAttribute(ID);
            User user = userService.findById(userId);

            if (!bidService.createBid(lot, user)) {
                redirect(request, response, AUCTION + "?" + LOT_ID + "=" + lotId + "&" +
                        MESSAGE + "=" + INSUFFICIENT_FUNDS);
                return;
            }

            redirect(request, response, AUCTION + "?" + LOT_ID + "=" + lotId);
        } catch (ServiceException | NumberFormatException e) {
            logger.error("ShowPriceCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
