package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_DOWN;

public class AddNewLot implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") != null) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String location = request.getParameter("location");
            String startPriceStr = request.getParameter("startPrice");
            String minPriceStr = request.getParameter("minPrice"); //проверка, min меньше start

            if (name != null & description != null & location != null & startPriceStr != null & minPriceStr != null) {
                //if (!name.equals("") & !description.equals("") & !location.equals("") & !startPrice.equals("")) {
                int ownerId = (int) session.getAttribute("id");
                BigDecimal startPrice = new BigDecimal(startPriceStr).setScale(4, ROUND_DOWN);
                BigDecimal minPrice = new BigDecimal(minPriceStr).setScale(4, ROUND_DOWN);
                //проверить min на ""
                Lot lot = new Lot(name, description, location, ownerId, startPrice, minPrice);

                ServiceFactory factory = ServiceFactory.getInstance();
                LotService lotService = factory.getLotService();

                try {
                    lotService.save(lot);
                } catch (ServiceException e) {
                    //
                }
            }else{
                return "addNewLot";
            }
        } else {
            //////////403 по сути
        }


        return "greeting";
    }
}
