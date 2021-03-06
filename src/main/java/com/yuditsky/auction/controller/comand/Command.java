package com.yuditsky.auction.controller.comand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Serves a request
 */
public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
