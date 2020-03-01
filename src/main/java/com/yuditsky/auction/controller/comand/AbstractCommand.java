package com.yuditsky.auction.controller.comand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractCommand implements Command {
    public void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    public void redirect(HttpServletRequest request, HttpServletResponse response, String page) throws IOException {
        response.sendRedirect(request.getContextPath() + page);
    }
}
