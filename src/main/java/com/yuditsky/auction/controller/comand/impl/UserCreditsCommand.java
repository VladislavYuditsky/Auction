package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.impl.util.PaginationHelper;
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

import static com.yuditsky.auction.controller.comand.impl.util.PaginationHelper.NUMBER_OF_RECORDS_ON_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.USER_CREDITS_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.CREDITS;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.*;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ID;

/**
 * Serves the user_credits request.
 * This command available only for authorized users.
 */
public class UserCreditsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(UserCreditsCommand.class);

    private final CreditService creditService;
    private final PaginationHelper paginationHelper;

    public UserCreditsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        creditService = factory.getCreditService();

        paginationHelper = new PaginationHelper();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int currentUserId = (int) session.getAttribute(ID);

        int currentPage;
        try {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } catch (NumberFormatException e) {
            currentPage = 0;
        }

        try {
            List<Credit> credits = creditService.findByBorrowerId(currentUserId, NUMBER_OF_RECORDS_ON_PAGE,
                    currentPage * NUMBER_OF_RECORDS_ON_PAGE);
            int pagesNumber = paginationHelper.calculatePagesNumber(creditService.findByBorrowerId(currentUserId),
                    NUMBER_OF_RECORDS_ON_PAGE);

            request.setAttribute(CURRENT_PAGE, currentPage);
            request.setAttribute(PAGES_NUMBER, pagesNumber);
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
