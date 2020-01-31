package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class ReplenishBalance implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String replenishSumStr = request.getParameter("replenishSum");

            ServiceFactory factory = ServiceFactory.getInstance();
            UserService userService = factory.getUserService();

            int userId = (Integer) session.getAttribute("id");

            try {
                User user = userService.findById(userId);

                if (replenishSumStr != null) {
                    BigDecimal replenishSum = new BigDecimal(replenishSumStr);

                    userService.addBalance(user, replenishSum);
                }

                BigDecimal balance = user.getBalance(); //validation// negative balance

                request.setAttribute("balance", balance);

                return "replenishBalance";
            } catch (ServiceException e) {
                /////
            }
        }

        return "signIn";
    }
}
