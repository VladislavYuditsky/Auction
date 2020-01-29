package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignOut implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "signIn";
    }
}
