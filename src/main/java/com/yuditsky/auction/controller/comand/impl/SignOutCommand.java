package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;
import com.yuditsky.auction.controller.comand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

import static com.yuditsky.auction.controller.comand.ConstProv.SIGN_IN_PAGE;

public class SignOutCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Enumeration<String> names = session.getAttributeNames();

        while (names.hasMoreElements()) {
            session.removeAttribute(names.nextElement());
        }

        forward(request, response, SIGN_IN_PAGE);
        //return "signIn";
    }
}
