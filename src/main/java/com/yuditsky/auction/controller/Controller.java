package com.yuditsky.auction.controller;

import com.yuditsky.auction.controller.comand.Command;
import com.yuditsky.auction.controller.comand.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private final CommandProvider commandProvider = new CommandProvider();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandName = request.getServletPath();
        commandName = commandName.substring(1);

        Command executionCommand = commandProvider.getCommand(commandName);

        executionCommand.execute(request, response);
    }
}

