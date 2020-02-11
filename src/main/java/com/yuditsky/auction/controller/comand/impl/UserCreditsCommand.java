package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.yuditsky.auction.controller.comand.ConstProv.USER_CREDITS_PAGE;

public class UserCreditsCommand extends AbstractCommand {
    private final CreditService creditService;

    public UserCreditsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        creditService = factory.getCreditService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int currentUserId = (Integer) session.getAttribute("id");

        try {
            List<Credit> credits = creditService.findByBorrowerId(currentUserId);

            request.setAttribute("credits", credits);

            forward(request, response, USER_CREDITS_PAGE);
        } catch (ServiceException e) {
            ///////
        }
    }
}
