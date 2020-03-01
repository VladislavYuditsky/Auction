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
import static com.yuditsky.auction.controller.provider.JspPageProvider.SIGN_IN_PAGE;
import static com.yuditsky.auction.controller.provider.MessageProvider.WRONG_LOGIN_OR_PASSWORD;
import static com.yuditsky.auction.controller.provider.RequestAttributesNameProvider.MESSAGE;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.LOGIN;
import static com.yuditsky.auction.controller.provider.RequestParametersNameProvider.PASSWORD;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.GREETING;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.*;

public class SignInCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(SignInCommand.class);

    private UserService userService;

    public SignInCommand() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        if (login != null && password != null) {
            try {
                User user = userService.findByLoginAndPassword(login, password);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute(ID, user.getId());
                    session.setAttribute(LOGIN, user.getLogin());
                    session.setAttribute(ROLE, user.getRole());
                    session.setAttribute(BLOCKED, user.isBlocked());

                    redirect(request, response, GREETING);
                } else {
                    request.setAttribute(PASSWORD, password);
                    request.setAttribute(LOGIN, login);
                    request.setAttribute(MESSAGE, WRONG_LOGIN_OR_PASSWORD);

                    forward(request, response, SIGN_IN_PAGE);
                }

            } catch (ServiceException e) {
                logger.error("SignInCommand failed", e);
                forward(request, response, ERROR_PAGE);
            }
        } else {
            forward(request, response, SIGN_IN_PAGE);
        }
    }
}
