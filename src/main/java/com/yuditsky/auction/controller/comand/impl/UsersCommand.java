package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.USERS_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributeNameProvider.USERS;

/**
 * Serves the users request.
 * This command available only for admin.
 */
public class UsersCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(UsersCommand.class);

    private final UserService userService;

    public UsersCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = userService.findAll();

            request.setAttribute(USERS, users);

            forward(request, response, USERS_PAGE);
        } catch (ServiceException e) {
            logger.error("UsersCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
