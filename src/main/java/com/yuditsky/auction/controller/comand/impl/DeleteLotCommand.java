package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Auction;
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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            if(lotIdStr != null){
                int lotId = Integer.parseInt(lotIdStr);

                ServiceFactory factory = ServiceFactory.getInstance();
                LotService lotService = factory.getLotService();
                AuctionService auctionService = factory.getAuctionService();

                try {
                    Lot lot = lotService.findById(lotId);

                    int ownerId = lot.getOwnerId();
                    int currentUserId = (Integer) session.getAttribute("id");

                    if(ownerId == currentUserId){

                        if(auctionService.findByLotId(lotId) == null) {
                            lotService.delete(lot);
                        }

                        request.setAttribute("id", currentUserId); //a nado li

                        //return "userLots"; //redirect
                        redirect(request, response, USER_LOTS);
                    } // else 403
                } catch (ServiceException e) {
                    ///
                }
            }
        }

        //return "greeting";
    }
}
