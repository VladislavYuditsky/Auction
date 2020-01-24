package com.yuditsky.auction.service;

import com.yuditsky.auction.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    private UserService userServiceImpl = new UserServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userServiceImpl;
    }
}
