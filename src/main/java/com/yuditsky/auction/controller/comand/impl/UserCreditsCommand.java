package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.yuditsky.auction.controller.comand.ConstProv.USER_CREDITS_PAGE;

public class UserCreditsCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String repaidSumStr = request.getParameter("repaidSum");
            String creditIdStr = request.getParameter("creditId");

            ServiceFactory factory = ServiceFactory.getInstance();
            CreditService creditService = factory.getCreditService();
            UserService userService = factory.getUserService();

            try {
                int currentUserId = (Integer) session.getAttribute("id");

                if (repaidSumStr != null & creditIdStr != null) {
                    BigDecimal repaidSum = new BigDecimal(repaidSumStr);
                    int creditId = Integer.parseInt(creditIdStr);

                    Credit credit = creditService.findById(creditId);

                    if(currentUserId == credit.getBorrowerId()){
                        User user = userService.findById(currentUserId);

                        if(user.getBalance().compareTo(repaidSum) > 0) {
                            userService.subtractBalance(user, repaidSum);
                            creditService.subtractBalance(creditId, repaidSum);
                        }
                    }//else 403
                }

                List<Credit> credits = creditService.findByBorrowerId(currentUserId);

                request.setAttribute("credits", credits);

                forward(request, response, USER_CREDITS_PAGE);
                //return "userCredits";
            } catch (ServiceException e) {
                ///////
            }
        }

        //return "signIn";
    }
}
