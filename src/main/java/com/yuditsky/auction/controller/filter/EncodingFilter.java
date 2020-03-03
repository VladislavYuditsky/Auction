package com.yuditsky.auction.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Sets utf-8 encoding for request.
 */
public class EncodingFilter implements Filter {
    private static final String ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        chain.doFilter(request, response);
    }
}