package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.AuctionService;
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
import static com.yuditsky.auction.controller.provider.MessageProvider.AUCTION_NOT_EDITED;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.*;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.USER_LOTS;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ID;

/**
 * Serves the update_lot request.
 * This command available only for authorized users.
 */
public class UpdateLotCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(UpdateLotCommand.class);

    private final LotService lotService;
    private final AuctionService auctionService;

    public UpdateLotCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int lotId = Integer.parseInt(request.getParameter(LOT_ID));
            Lot lot = lotService.findById(lotId);

            if (lot != null) {
                HttpSession session = request.getSession();
                int currentUserId = (Integer) session.getAttribute(ID);

                if (lot.getOwnerId() == currentUserId) {

                    if (auctionService.findByLotId(lotId) == null) {
                        String name = request.getParameter(NAME);
                        String description = request.getParameter(DESCRIPTION);
                        String location = request.getParameter(LOCATION);
                        String startPriceStr = request.getParameter(START_PRICE);


                        BigDecimal startPrice = new BigDecimal(startPriceStr);

                        lot.setName(name);
                        lot.setDescription(description);
                        lot.setLocation(location);
                        lot.setStartPrice(startPrice);

                        lotService.update(lot);

                        redirect(request, response, USER_LOTS);
                    } else {
                        request.setAttribute(MESSAGE, AUCTION_NOT_EDITED);
                        request.setAttribute(LOT_ID, lotId);

                        forward(request, response, USER_LOTS);
                    }
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("UpdateLotCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
