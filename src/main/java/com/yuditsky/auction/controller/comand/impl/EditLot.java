package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class EditLot implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            if (lotIdStr != null) {

                int lotId = Integer.parseInt(lotIdStr);

                ServiceFactory factory = ServiceFactory.getInstance();
                LotService lotService = factory.getLotService();

                try {
                    Lot lot = lotService.findById(lotId);

                    int currentUserId = (Integer) session.getAttribute("id");

                    if(lot.getOwnerId() == currentUserId) {

                        String name = request.getParameter("name");
                        String description = request.getParameter("description");
                        String location = request.getParameter("location");
                        String startPriceStr = request.getParameter("startPrice");

                        if (name != null && description != null && location != null && startPriceStr != null) {
                            //validation в том числе на пустую строку

                            BigDecimal startPrice = new BigDecimal(startPriceStr);

                            lot.setName(name);
                            lot.setDescription(description);
                            lot.setLocation(location);
                            lot.setStartPrice(startPrice);

                            lotService.update(lot);

                            return "userLots"; //redirect(user_lots?id=${session.getAttr("id)
                        }

                        request.setAttribute("lot", lot);

                        return "editLot";
                    }//else 403
                } catch (ServiceException e) {
                    ///
                }
            }
        }

        return "greeting";
    }
}
