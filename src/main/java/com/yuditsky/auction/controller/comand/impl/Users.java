package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class Users implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            UserRole role = (UserRole) session.getAttribute("role");

            if (role == UserRole.ADMIN) {
                ServiceFactory factory = ServiceFactory.getInstance();
                UserService userService = factory.getUserService();

                try {
                    List<User> users = userService.findAll();

                    request.setAttribute("users", users);

                    return "users";
                } catch (ServiceException e) {
                    ///
                }
            } else{
                //403
            }
        }

        return "signIn";
    }
}
