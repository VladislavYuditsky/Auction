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
import java.util.List;

import static com.yuditsky.auction.controller.comand.ConstProv.USER_LOTS_PAGE;

public class UserLotsCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            int userId = (Integer) session.getAttribute("id");

            ServiceFactory factory = ServiceFactory.getInstance();
            LotService lotService = factory.getLotService();

            try {
                List<Lot> lots = lotService.findByOwnerId(userId);

                request.setAttribute("lots", lots);

                forward(request, response, USER_LOTS_PAGE);
                //return "userLots";
            } catch (ServiceException e) {
                //
            }
        }
        //return "signIn";
    }
}
