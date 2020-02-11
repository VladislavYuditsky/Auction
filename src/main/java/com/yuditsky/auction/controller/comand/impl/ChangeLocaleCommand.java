package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class ChangeLocaleCommand extends AbstractCommand {
    private Locale localeRU = new Locale("ru", "RU");
    private Locale localeEN = new Locale("en", "US");

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute("locale");

        if (locale.equals(localeRU)) {
            session.setAttribute("locale", localeEN);
        } else {
            session.setAttribute("locale", localeRU);
        }

        String page = request.getParameter("page");

        page = page.replace(".jsp", "");

        String query = request.getParameter("query");

        redirect(request, response, page + "?" + query);
    }
}
