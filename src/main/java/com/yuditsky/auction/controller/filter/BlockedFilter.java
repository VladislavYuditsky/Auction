package com.yuditsky.auction.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.ServletPathProvider.BAN;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.BLOCKED;

/**
 * Filters user requests depending on whether they are blocked or not.
 */
public class BlockedFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String path = request.getServletPath();

        if (session.getAttribute(BLOCKED) != null) {
            boolean blocked = (boolean) session.getAttribute(BLOCKED);
            if (blocked && !path.equals(BAN)) {
                response.sendRedirect(request.getContextPath() + BAN);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
