package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.impl.util.PaginationHelper;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
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
import static com.yuditsky.auction.controller.provider.JspPageProvider.USER_LOTS_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.LOTS;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.CURRENT_PAGE;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.PAGES_NUMBER;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ID;

/**
 * Serves the user_lots request.
 * This command available only for authorized users.
 */
public class UserLotsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(UserLotsCommand.class);

    private LotService lotService;
    private final PaginationHelper paginationHelper;

    public UserLotsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();

        paginationHelper = new PaginationHelper();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(ID);

        int currentPage;
        try {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } catch (NumberFormatException e) {
            currentPage = 0;
        }

        try {
            List<Lot> lots = lotService.findByOwnerId(userId, NUMBER_OF_RECORDS_ON_PAGE,
                    currentPage * NUMBER_OF_RECORDS_ON_PAGE);
            int pagesNumber = paginationHelper.calculatePagesNumber(lotService.findByOwnerId(userId),
                    NUMBER_OF_RECORDS_ON_PAGE);

            request.setAttribute(CURRENT_PAGE, currentPage);
            request.setAttribute(PAGES_NUMBER, pagesNumber);
            request.setAttribute(LOTS, lots);

            forward(request, response, USER_LOTS_PAGE);
        } catch (ServiceException e) {
            logger.error("UserLotsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
