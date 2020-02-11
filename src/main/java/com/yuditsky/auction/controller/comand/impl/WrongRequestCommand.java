package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yuditsky.auction.controller.comand.ConstProv.BAN_PAGE;
import static com.yuditsky.auction.controller.comand.ConstProv.WRONG_REQUEST_PAGE;

public class WrongRequestCommand extends AbstractCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        forward(request, response, WRONG_REQUEST_PAGE);
    }
}
