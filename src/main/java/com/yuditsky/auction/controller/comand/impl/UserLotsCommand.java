package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.USER_LOTS_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.LOTS;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class UserLotsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(UserLotsCommand.class);

    private LotService lotService;

    public UserLotsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(ID);

        try {
            List<Lot> lots = lotService.findByOwnerId(userId);

            request.setAttribute(LOTS, lots);

            forward(request, response, USER_LOTS_PAGE);
        } catch (ServiceException e) {
            logger.error("UserLotsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
