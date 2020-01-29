package com.yuditsky.auction.controller.comand.impl;

import com.yuditsky.auction.controller.comand.Command;

import javax.servlet.http.HttpServletRequest;

public class Greeting implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "greeting";
    }
}
