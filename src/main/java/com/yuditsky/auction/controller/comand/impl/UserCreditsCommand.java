package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.USER_CREDITS_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.CREDITS;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.CREDIT_ID;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class UserCreditsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(UserCreditsCommand.class);

    private final CreditService creditService;

    public UserCreditsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        creditService = factory.getCreditService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int currentUserId = (int) session.getAttribute(ID);

        try {
            List<Credit> credits = creditService.findByBorrowerId(currentUserId);

            request.setAttribute(CREDIT_ID, request.getParameter(CREDIT_ID));
            request.setAttribute(MESSAGE, request.getParameter(MESSAGE));
            request.setAttribute(CREDITS, credits);

            forward(request, response, USER_CREDITS_PAGE);
        } catch (ServiceException e) {
            logger.error("UserCreditsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
