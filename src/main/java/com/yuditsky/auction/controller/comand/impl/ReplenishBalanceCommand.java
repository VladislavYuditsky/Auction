package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.comand.ConstProv.USER_BALANCE;

public class ReplenishBalanceCommand extends AbstractCommand {
    private final UserService userService;

    public ReplenishBalanceCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String replenishSumStr = request.getParameter("replenishSum");

        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("id");

        try {
            User user = userService.findById(userId);

            if (replenishSumStr != null && !replenishSumStr.equals("")) {
                BigDecimal replenishSum = new BigDecimal(replenishSumStr);

                userService.addBalance(user, replenishSum);
            }

            redirect(request, response, USER_BALANCE);
        } catch (ServiceException e) {
            /////
        }
    }
}
