package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static com.yuditsky.auction.controller.comand.ConstProv.CREATE_LOT_PAGE;
import static com.yuditsky.auction.controller.comand.ConstProv.USER_LOTS;
import static java.math.BigDecimal.ROUND_DOWN;

public class CreateLotCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") != null) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String location = request.getParameter("location");
            String startPriceStr = request.getParameter("startPrice");

            if (name != null & description != null & location != null & startPriceStr != null) { //если не проверить на "" упадёт
                //if (!name.equals("") & !description.equals("") & !location.equals("") & !startPrice.equals("")) {
                int ownerId = (int) session.getAttribute("id");
                BigDecimal startPrice = new BigDecimal(startPriceStr).setScale(4, ROUND_DOWN);
                Lot lot = new Lot(name, description, location, ownerId, startPrice);

                ServiceFactory factory = ServiceFactory.getInstance();
                LotService lotService = factory.getLotService();

                try {
                    lotService.save(lot);
                } catch (ServiceException e) {
                    //
                }
            }else{
                //return "addNewLot";
                forward(request, response, CREATE_LOT_PAGE);
                return;
            }
        } else {
            //////////403 по сути
        }


        //return "userLots"; //redirect
        redirect(request, response, USER_LOTS );
    }
}
