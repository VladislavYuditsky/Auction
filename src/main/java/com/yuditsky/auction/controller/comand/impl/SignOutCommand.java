package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.SIGN_IN_PAGE;

public class SignOutCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.removeAttribute("id");
        session.removeAttribute("login");
        session.removeAttribute("role");
        session.removeAttribute("blocked");

        forward(request, response, SIGN_IN_PAGE);
    }
}
