package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AwaitingPayment implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            LotService lotService = factory.getLotService();

            int id = (Integer) session.getAttribute("id");

            try {
                List<Lot> lots = lotService.findByBuyerId(id);

                request.setAttribute("lots", lots);

                return "awaitingPaymentLots";
            } catch (ServiceException e) {
                //////
            }
        }

        return "signIn";
    }
}
