package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Block implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            UserRole userRole = (UserRole) session.getAttribute("role");

            if (userRole == UserRole.ADMIN) {
                String userIdStr = request.getParameter("userId");

                if(userIdStr != null){
                    int userId = Integer.parseInt(userIdStr);

                    ServiceFactory factory = ServiceFactory.getInstance();
                    UserService userService = factory.getUserService();

                    try {
                        User user = userService.findById(userId);

                        user.setBlocked(true);

                        userService.update(user);

                        return "proposedAuctions"; //redirect на deny
                    } catch (ServiceException e) {
                        ///
                    }
                }
            }//else 403
        }

        return "greeting";
    }
}
