package com.yuditsky.auction.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.*;

public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        String path = request.getServletPath();
        if (!path.equals(SIGN_IN) && !path.equals(SIGN_UP) && !path.equals(GREETING) && !path.equals(AUCTIONS)
                && session.getAttribute("id") == null) {
            response.sendRedirect(request.getContextPath() + SIGN_IN);
        } else {
            chain.doFilter(request, response);
        }
    }
}
