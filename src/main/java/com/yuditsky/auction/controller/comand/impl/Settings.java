package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import com.yuditsky.auction.service.util.Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Settings implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            UserService userService = factory.getUserService();

            String password = request.getParameter("password");
            String email = request.getParameter("email");

            int id = (Integer) session.getAttribute("id");

            try {
                User user = userService.findById(id);

                if (password != null && !password.equals("")) {
                    Encoder encoder = new Encoder(); //v service
                    user.setPassword(encoder.encode(password));
                }

                if (email != null && !email.equals("")) {
                    user.setEmail(email);
                }

                if (password != null | email != null) {
                    userService.update(user);
                }

                request.setAttribute("email", user.getEmail());

                return "settings";
            } catch (ServiceException e) {
                /////
            }
        }

        return "signIn";
    }
}
