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

import static com.yuditsky.auction.controller.comand.ConstProv.AUCTIONS;
import static com.yuditsky.auction.controller.comand.ConstProv.SIGN_IN_PAGE;

public class SignInCommand extends AbstractCommand {
    private final UserService userService;

    public SignInCommand() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login != null & password != null) { //пустые строки
            try {
                User user = userService.findByLoginAndPassword(login, password);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("id", user.getId());
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("role", user.getRole());
                    session.setAttribute("blocked", user.isBlocked());

                    redirect(request, response, AUCTIONS);
                    return;
                }

            } catch (ServiceException e) {
                ///
            }
        }

        forward(request, response, SIGN_IN_PAGE);
    }
}
