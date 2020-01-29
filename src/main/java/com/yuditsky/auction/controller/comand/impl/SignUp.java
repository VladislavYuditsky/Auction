package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignUp implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");
        String email = request.getParameter("email");

        String page = "signUp";
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
                        page = "signIn";
                    }

                } catch (ServiceException e) {
                    ///
                }
            }
        }

        return page;
    }
}
