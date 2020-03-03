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
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.EMAIL;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.PASSWORD;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.SETTINGS;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ID;

/**
 * Serves the update_settings request.
 * This command available only for authorized users.
 */
public class UpdateSettingsCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(UpdateSettingsCommand.class);

    private final UserService userService;

    public UpdateSettingsCommand() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);

        HttpSession session = request.getSession();
        int id = (int) session.getAttribute(ID);

        try {
            User user = userService.findById(id);

            userService.updateSettings(user, email, password);

            redirect(request, response, SETTINGS);
        } catch (ServiceException e) {
            logger.error("UpdateSettingsCommand failed", e);
            forward(request, response, ERROR_PAGE);
        }
    }
}
