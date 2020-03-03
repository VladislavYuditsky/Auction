package com.yuditsky.auction.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.ServletPathProvider.*;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ID;

/**
 * Filters user requests depending on whether they are authorized or not.
 */
public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        String path = request.getServletPath();
        if (!path.equals(SIGN_IN) && !path.equals(SIGN_UP) && !path.equals(CREATE_USER) && !path.equals(GREETING)
                && !path.equals(AUCTIONS) && !path.equals(CHANGE_LOCALE) && session.getAttribute(ID) == null) {
            response.sendRedirect(request.getContextPath() + SIGN_IN);
        } else {
            chain.doFilter(request, response);
        }
    }
}
