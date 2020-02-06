package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.DENY;

public class BlockCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

                        //return "proposedAuctions"; //redirect на deny
                        redirect(request, response, DENY);
                    } catch (ServiceException e) {
                        ///
                    }
                }
            }//else 403
        }

        //return "greeting";
    }
}
