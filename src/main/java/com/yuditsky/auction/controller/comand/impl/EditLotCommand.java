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

import static com.yuditsky.auction.controller.provider.JspPageProvider.EDIT_LOT_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.MessageProvider.AUCTION_NOT_EDITED;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.LOT;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.LOT_ID;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.USER_LOTS;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class EditLotCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(EditLotCommand.class);

    private final LotService lotService;
    private final AuctionService auctionService;

    public EditLotCommand() {
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
                        request.setAttribute(LOT, lot);
                        forward(request, response, EDIT_LOT_PAGE);
                    } else {
                        request.setAttribute(MESSAGE, AUCTION_NOT_EDITED);
                        request.setAttribute(LOT_ID, lotId);

                        forward(request, response, USER_LOTS);
                    }
                }
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("EditLotCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
