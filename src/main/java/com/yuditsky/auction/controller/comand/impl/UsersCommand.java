package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.yuditsky.auction.controller.comand.ConstProv.USERS_PAGE;

public class UsersCommand extends AbstractCommand {
    private final UserService userService;

    public UsersCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = userService.findAll();

            request.setAttribute("users", users);

            forward(request, response, USERS_PAGE);
        } catch (ServiceException e) {
            ///
        }
    }
}
