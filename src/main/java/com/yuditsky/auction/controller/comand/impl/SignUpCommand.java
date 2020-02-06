package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.SIGN_IN;
import static com.yuditsky.auction.controller.comand.ConstProv.SIGN_UP;

public class SignUpCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");
        String email = request.getParameter("email");

        String page = SIGN_UP;
        //String page = "sign_in";
        if (login != null & password != null & confPassword != null & email != null) {
            if(password.equals(confPassword)) {
                try {
                    User user = new User(login, password, email);

                    ServiceFactory serviceFactory = ServiceFactory.getInstance();
                    UserService userService = serviceFactory.getUserService();

                    if(userService.save(user)) {

                        user = userService.findByLogin(login);

                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        page = SIGN_IN;
                    }

                } catch (ServiceException e) {
                    ///
                }
            }
        }

        redirect(request, response, page); //на signIn forward&
        //return page;
    }
}
