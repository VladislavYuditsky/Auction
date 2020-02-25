package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.User;
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
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.USER_BALANCE_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.BALANCE;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class UserBalanceCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(UserBalanceCommand.class);

    private final UserService userService;

    public UserBalanceCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(ID);

        try {
            User user = userService.findById(userId);

            BigDecimal balance = user.getBalance();

            request.setAttribute(BALANCE, balance);

            forward(request, response, USER_BALANCE_PAGE);
        } catch (ServiceException e) {
            logger.error("UserBalanceCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
