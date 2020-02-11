package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
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

import static com.yuditsky.auction.controller.comand.ConstProv.USER_CREDITS;

public class RepayCreditCommand extends AbstractCommand {
    private final CreditService creditService;
    private final UserService userService;

    public RepayCreditCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        creditService = factory.getCreditService();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String repaidSumStr = request.getParameter("repaidSum");
        String creditIdStr = request.getParameter("creditId");

        try {
            if (repaidSumStr != null & creditIdStr != null && !repaidSumStr.equals("")) {
                BigDecimal repaidSum = new BigDecimal(repaidSumStr);
                int creditId = Integer.parseInt(creditIdStr);

                Credit credit = creditService.findById(creditId);

                HttpSession session = request.getSession();
                int currentUserId = (Integer) session.getAttribute("id");

                if (currentUserId == credit.getBorrowerId()) {
                    User user = userService.findById(currentUserId);

                    if(!creditService.subtractBalance(creditId, repaidSum, user)){
                        //сообщение пользователю
                    }
                }//else 403
            }

            redirect(request, response, USER_CREDITS);
        } catch (ServiceException e) {
            ///////
        }
    }
}
