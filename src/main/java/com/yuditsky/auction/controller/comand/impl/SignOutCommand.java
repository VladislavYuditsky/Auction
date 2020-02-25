package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.JspPageProvider.SIGN_IN_PAGE;
import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.*;

public class SignOutCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.removeAttribute(ID);
        session.removeAttribute(LOGIN);
        session.removeAttribute(ROLE);
        session.removeAttribute(BLOCKED);

        forward(request, response, SIGN_IN_PAGE);
    }
}
