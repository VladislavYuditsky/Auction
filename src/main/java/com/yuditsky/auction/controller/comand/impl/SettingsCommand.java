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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.SETTINGS_PAGE;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.EMAIL;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.ID;

public class SettingsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(SettingsCommand.class);

    private final UserService userService;

    public SettingsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute(ID);

        try {
            User user = userService.findById(id);

            request.setAttribute(EMAIL, user.getEmail());

            forward(request, response, SETTINGS_PAGE);
        } catch (ServiceException e) {
            logger.error("SettingsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
