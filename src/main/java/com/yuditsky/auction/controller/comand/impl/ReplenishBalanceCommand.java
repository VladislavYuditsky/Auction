package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.comand.ConstProv.REPLENISH_BALANCE;
import static com.yuditsky.auction.controller.comand.ConstProv.USER_BALANCE;

public class ReplenishBalanceCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

                //BigDecimal balance = user.getBalance(); //validation// negative balance

                //request.setAttribute("balance", balance);

                //return "replenishBalance";
                redirect(request, response, USER_BALANCE);
            } catch (ServiceException e) {
                /////
            }
        }

        //return "signIn";
    }
}
