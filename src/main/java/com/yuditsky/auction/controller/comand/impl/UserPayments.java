package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.Payment;
import com.yuditsky.auction.service.PaymentService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserPayments implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            PaymentService paymentService = factory.getPaymentService();

            int userId = (Integer) session.getAttribute("id");

            try {
                List<Payment> payments = paymentService.findByPayerId(userId);

                request.setAttribute("payments", payments);

                return "userPayments";
            } catch (ServiceException e) {
                ////
            }

        }
        return "signIn";
    }
}
