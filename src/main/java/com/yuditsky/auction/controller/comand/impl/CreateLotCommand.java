package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
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
    private final LotService lotService;

    public CreateLotCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String startPriceStr = request.getParameter("startPrice");

        if (name != null & description != null & location != null & startPriceStr != null && !startPriceStr.equals("")) { //если не проверить на "" упадёт
            //if (!name.equals("") & !description.equals("") & !location.equals("") & !startPrice.equals("")) {
            HttpSession session = request.getSession();
            int ownerId = (int) session.getAttribute("id");
            BigDecimal startPrice = new BigDecimal(startPriceStr).setScale(4, ROUND_DOWN);
            Lot lot = new Lot(name, description, location, ownerId, startPrice);

            try {
                lotService.save(lot);
            } catch (ServiceException e) {
                //
            }
        } else {
            forward(request, response, CREATE_LOT_PAGE);
            return;
        }

        redirect(request, response, USER_LOTS);
    }
}
