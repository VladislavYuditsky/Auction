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

import static com.yuditsky.auction.controller.provider.JspPageProvider.ERROR_PAGE;
import static com.yuditsky.auction.controller.provider.JspPageProvider.SIGN_UP_PAGE;
import static com.yuditsky.auction.controller.provider.MessageProvider.USER_ALREADY_EXISTS;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.*;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.SIGN_IN;

/**
 * Serves the create_user request.
 * This command available only for not authorized users.
 */
public class CreateUserCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(CreateUserCommand.class);

    private final UserService userService;

    public CreateUserCommand() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String confPassword = request.getParameter(CONFIRMATION_PASSWORD);
        String email = request.getParameter(EMAIL);

        if (password.equals(confPassword)) {
            try {
                User user = new User(login, password, email);

                if (userService.save(user)) {
                    redirect(request, response, SIGN_IN);
                } else {
                    request.setAttribute(LOGIN, login);
                    request.setAttribute(PASSWORD, password);
                    request.setAttribute(EMAIL, email);
                    request.setAttribute(MESSAGE, USER_ALREADY_EXISTS);
                    forward(request, response, SIGN_UP_PAGE);
                }
            } catch (ServiceException e) {
                logger.error("CreateUserCommand failed", e);
                forward(request, response, ERROR_PAGE);
            }
        }
    }
}
