package com.yuditsky.auction.controller.filter;

import com.yuditsky.auction.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.yuditsky.auction.controller.provider.ServletPathProvider.*;
import static com.yuditsky.auction.controller.provider.SessionAttributeNameProvider.ROLE;

/**
 * Filters user requests based on their role.
 */
public class RoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        UserRole role = (UserRole) session.getAttribute(ROLE);

        String path = request.getServletPath();
        if (role == UserRole.USER && (path.equals(CHANGE_BLOCK_STATUS) || path.equals(CHANGE_AUCTION_STATUS) || path.equals(DENY) ||
                path.equals(USERS) || path.equals(PROPOSED_AUCTIONS))) {
            response.sendRedirect(request.getContextPath() + SIGN_IN);
        }

        chain.doFilter(request, response);
    }
}
