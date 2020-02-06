package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.comand.ConstProv.USER_BALANCE_PAGE;

public class UserBalanceCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            UserService userService = factory.getUserService();

            int userId = (Integer) session.getAttribute("id");

            try {
                User user = userService.findById(userId);

                BigDecimal balance = user.getBalance(); //validation// negative balance

                request.setAttribute("balance", balance);

                //return "replenishBalance";
                forward(request, response, USER_BALANCE_PAGE);
            } catch (ServiceException e) {
                /////
            }
        }

        //return "signIn";
    }
}
