package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.BAN_PAGE;

public class BanCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        session.removeAttribute("id");
        session.removeAttribute("login");
        session.removeAttribute("role");
        session.removeAttribute("blocked");

        forward(request, response, BAN_PAGE);
    }
}
