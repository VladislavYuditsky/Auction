package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
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
import static com.yuditsky.auction.controller.provider.ServletPathProvider.USER_LOTS;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ID;

/**
 * Serves the buy request.
 * This command available only for authorized users.
 */
public class BuyCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(BuyCommand.class);

    private final UserService userService;
    private final LotService lotService;

    public BuyCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
        lotService = factory.getLotService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int lotId = Integer.parseInt(request.getParameter(LOT_ID));

            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute(ID);

            User user = userService.findById(userId);
            Lot lot = lotService.findById(lotId);

            if (lotService.buy(lot, user)) {
                redirect(request, response, USER_LOTS);
            } else {
                redirect(request, response, AUCTION + "?" + LOT_ID + "=" + lotId + "&" +
                        MESSAGE + "=" + INSUFFICIENT_FUNDS);
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("BuyCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
