package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.MessageProvider.INSUFFICIENT_FUNDS;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.CREDIT_ID;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.REPAID_SUM;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.USER_CREDITS;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ID;

/**
 * Serves the repay_credit request.
 * This command available only for authorized users.
 */
public class RepayCreditCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(RepayCreditCommand.class);

    private final CreditService creditService;
    private final UserService userService;

    public RepayCreditCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        creditService = factory.getCreditService();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            BigDecimal repaidSum = new BigDecimal(request.getParameter(REPAID_SUM));
            int creditId = Integer.parseInt(request.getParameter(CREDIT_ID));

            Credit credit = creditService.findById(creditId);

            HttpSession session = request.getSession();
            int currentUserId = (Integer) session.getAttribute(ID);

            if (currentUserId == credit.getBorrowerId()) {
                User user = userService.findById(currentUserId);

                if (!creditService.subtractBalance(creditId, repaidSum, user)) {
                    redirect(request, response, USER_CREDITS + "?" + MESSAGE + "=" + INSUFFICIENT_FUNDS + "&" +
                            CREDIT_ID + "=" + creditId);
                } else {
                    redirect(request, response, USER_CREDITS + "?" + CREDIT_ID + "=" + creditId);
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("RepayCreditCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
