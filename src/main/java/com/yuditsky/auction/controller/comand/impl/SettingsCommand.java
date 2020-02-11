package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.SETTINGS_PAGE;

public class SettingsCommand extends AbstractCommand {
    private final UserService userService;

    public SettingsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute("id");

        try {
            User user = userService.findById(id);

            request.setAttribute("email", user.getEmail());

            forward(request, response, SETTINGS_PAGE);
        } catch (ServiceException e) {
            /////
        }
    }
}
