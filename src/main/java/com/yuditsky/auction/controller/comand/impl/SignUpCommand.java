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

import static com.yuditsky.auction.controller.comand.ConstProv.SIGN_IN;
import static com.yuditsky.auction.controller.comand.ConstProv.SIGN_UP_PAGE;

public class SignUpCommand extends AbstractCommand {
    private final UserService userService;

    public SignUpCommand() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");
        String email = request.getParameter("email");

        if (login != null & password != null & confPassword != null & email != null) {
            if (password.equals(confPassword)) {
                try {
                    User user = new User(login, password, email);

                    if (userService.save(user)) {

                        user = userService.findByLogin(login);

                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);

                        redirect(request, response, SIGN_IN);
                        return;
                    }

                } catch (ServiceException e) {
                    ///
                }
            }
        }

        forward(request, response, SIGN_UP_PAGE);
    }
}
