package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.USER_LOTS;

public class DeleteLotCommand extends AbstractCommand {
    private final LotService lotService;
    private final AuctionService auctionService;

    public DeleteLotCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
        auctionService = factory.getAuctionService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lotIdStr = request.getParameter("lotId");

        if (lotIdStr != null) {
            int lotId = Integer.parseInt(lotIdStr);

            try {
                Lot lot = lotService.findById(lotId);

                int ownerId = lot.getOwnerId();

                HttpSession session = request.getSession();
                int currentUserId = (Integer) session.getAttribute("id");

                if (ownerId == currentUserId) {

                    if (auctionService.findByLotId(lotId) == null) {
                        lotService.delete(lot);
                    }

                    redirect(request, response, USER_LOTS);
                } // else 403
            } catch (ServiceException e) {
                ///
            }
        }
    }
}
