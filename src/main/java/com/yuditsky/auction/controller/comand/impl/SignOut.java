package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class SignOut implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> names = session.getAttributeNames();

        while (names.hasMoreElements()) {
            session.removeAttribute(names.nextElement());
        }

        return "signIn";
    }
}
