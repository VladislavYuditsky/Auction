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

import static com.yuditsky.auction.controller.comand.ConstProv.*;

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

        String lotIdStr = request.getParameter("lotId");

        if (lotIdStr != null) {
            int lotId = Integer.parseInt(lotIdStr);

            HttpSession session = request.getSession();
            int userId = (Integer) session.getAttribute("id");

            try {
                User user = userService.findById(userId);

                Lot lot = lotService.findById(lotId);

                if (lotService.buy(lot, user)) {
                    redirect(request, response, USER_LOTS);
                } else {
                    redirect(request, response, AUCTION);
                }
            } catch (ServiceException e) {
                logger.error("BuyCommand failed", e);
                forward(request, response, ERROR_PAGE);
            }
        }
    }
}
