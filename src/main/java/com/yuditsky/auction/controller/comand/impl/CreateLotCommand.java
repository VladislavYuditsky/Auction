package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
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
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.*;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.USER_LOTS;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;
import static java.math.BigDecimal.ROUND_DOWN;

public class CreateLotCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(CreateLotCommand.class);

    private final LotService lotService;

    public CreateLotCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter(NAME);
        String description = request.getParameter(DESCRIPTION);
        String location = request.getParameter(LOCATION);
        String startPriceStr = request.getParameter(START_PRICE);

        HttpSession session = request.getSession();
        int ownerId = (int) session.getAttribute(ID);

        try {
            BigDecimal startPrice = new BigDecimal(startPriceStr).setScale(4, ROUND_DOWN);

            Lot lot = new Lot(name, description, location, ownerId, startPrice);

            lotService.save(lot);

            redirect(request, response, USER_LOTS);
        } catch (ServiceException | NumberFormatException e) {
            logger.error("CreateLotCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
