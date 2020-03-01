package com.yuditsky.auction.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static com.yuditsky.auction.controller.provider.SessionAttributesNameProvider.LOCALE;

public class LocaleFilter implements Filter {
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        if (session.getAttribute(LOCALE) == null) {
            session.setAttribute(LOCALE, DEFAULT_LOCALE);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
