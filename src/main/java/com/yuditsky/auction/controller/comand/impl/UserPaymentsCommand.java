package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.Payment;
import com.yuditsky.auction.service.PaymentService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.yuditsky.auction.controller.comand.ConstProv.USER_PAYMENTS_PAGE;

public class UserPaymentsCommand extends AbstractCommand {
    private final PaymentService paymentService;

    public UserPaymentsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        paymentService = factory.getPaymentService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("id");

        try {
            List<Payment> payments = paymentService.findByPayerId(userId);

            request.setAttribute("payments", payments);

            forward(request, response, USER_PAYMENTS_PAGE);
        } catch (ServiceException e) {
            ////
        }
    }
}
