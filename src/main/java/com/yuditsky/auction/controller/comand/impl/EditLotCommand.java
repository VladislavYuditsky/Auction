package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.comand.ConstProv.EDIT_LOT_PAGE;
import static com.yuditsky.auction.controller.comand.ConstProv.USER_LOTS;

public class EditLotCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            String lotIdStr = request.getParameter("lotId");

            if (lotIdStr != null) {

                int lotId = Integer.parseInt(lotIdStr);

                ServiceFactory factory = ServiceFactory.getInstance();
                LotService lotService = factory.getLotService();
                AuctionService auctionService = factory.getAuctionService();

                try {
                    Lot lot = lotService.findById(lotId);

                    int currentUserId = (Integer) session.getAttribute("id");

                    if (lot.getOwnerId() == currentUserId) {

                        if (auctionService.findByLotId(lotId) == null) {
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

                                //return "userLots"; //redirect(user_lots?id=${session.getAttr("id)
                                redirect(request, response, USER_LOTS);
                                return;
                            }

                            request.setAttribute("lot", lot);

                            //return "editLot";
                            forward(request, response, EDIT_LOT_PAGE);
                        } else {
                            //return "userLots"; //redirect(user_lots?id=${session.getAttr("id)
                            redirect(request, response, USER_LOTS);
                        }
                    }//else 403
                } catch (ServiceException e) {
                    ///
                }
            }
        }

        //return "greeting";
    }
}
