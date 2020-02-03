package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteLot implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            if(lotIdStr != null){
                int lotId = Integer.parseInt(lotIdStr);

                ServiceFactory factory = ServiceFactory.getInstance();
                LotService lotService = factory.getLotService();

                try {
                    Lot lot = lotService.findById(lotId);

                    int ownerId = lot.getOwnerId();
                    int currentUserId = (Integer) session.getAttribute("id");

                    if(ownerId == currentUserId){
                        lotService.delete(lot);

                        request.setAttribute("id", currentUserId);

                        return "userLots"; //redirect
                    } // else 403
                } catch (ServiceException e) {
                    ///
                }
            }
        }

        return "greeting";
    }
}
