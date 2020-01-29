package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignIn implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String page = "signIn";
        //String page = "sign_in";
        if(login!= null & password != null) { //пустые строки
            try {
                User user = userService.findByLoginAndPassword(login, password);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("id", user.getId());
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("role", user.getRole());
                    page = "main";
                }

            } catch (ServiceException e) {
                ///
            }
        }

        return page;
    }
}
