package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class UserCredits implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String repaidSumStr = request.getParameter("repaidSum");
            String creditIdStr = request.getParameter("creditId");

            ServiceFactory factory = ServiceFactory.getInstance();
            CreditService creditService = factory.getCreditService();

            if (repaidSumStr != null & creditIdStr != null) {
                BigDecimal repaidSum = new BigDecimal(repaidSumStr);
                int creditId = Integer.parseInt(creditIdStr);

                try {
                    creditService.subtractBalance(creditId, repaidSum);
                } catch (ServiceException e) {
                    /////
                }

            }

            int userId = (Integer) session.getAttribute("id");

            try {
                List<Credit> credits = creditService.findByBorrowerId(userId);

                request.setAttribute("credits", credits);

                return "userCredits";
            } catch (ServiceException e) {
                ///////
            }
        }

        return "signIn";
    }
}
