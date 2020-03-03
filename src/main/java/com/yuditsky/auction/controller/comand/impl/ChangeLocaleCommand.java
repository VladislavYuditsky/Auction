package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.PAGE;
import static com.yuditsky.auction.controller.provider.RequestParameterNameProvider.QUERY;
import static com.yuditsky.auction.controller.provider.ServletPathProvider.*;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.LOCALE;

/**
 * Serves the change_locale request.
 * This command available for all users.
 */
public class ChangeLocaleCommand extends AbstractCommand {
    private Locale localeRU = new Locale("ru", "RU");
    private Locale localeEN = new Locale("en", "US");

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE);

        if (locale.equals(localeRU)) {
            session.setAttribute(LOCALE, localeEN);
        } else {
            session.setAttribute(LOCALE, localeRU);
        }

        String page = request.getParameter(PAGE);
        page = page.replace("/jsp", "");
        page = page.replace(".jsp", "");

        if (page.equals(DIRECT_AUCTION) || page.equals(REVERS_AUCTION)) {
            page = AUCTION;
        }

        String query = request.getParameter(QUERY);

        redirect(request, response, page + "?" + query);
    }
}
