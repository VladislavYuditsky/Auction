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
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.controller.comand.ConstProv.AWAITING_PAYMENT_LOTS_PAGE;

public class AwaitingPaymentLotsCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            LotService lotService = factory.getLotService();

            int id = (Integer) session.getAttribute("id");

            try {
                List<Lot> lots = lotService.findAwaitingPaymentLots(id);

                request.setAttribute("lots", lots);

                //return "awaitingPaymentLots";
                forward(request, response, AWAITING_PAYMENT_LOTS_PAGE);

            } catch (ServiceException e) {
                //////
            }
        }

        //return "signIn";
    }
}
