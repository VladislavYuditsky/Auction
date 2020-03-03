package com.yuditsky.auction.controller.listener;

import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.controller.manager.UserBlockManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener on a servlet context.
 */
@WebListener
public class ContextListener implements ServletContextListener {
    private ConnectionPool connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        connectionPool = ConnectionPool.getInstance();
        UserBlockManager userBlockManager = new UserBlockManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        connectionPool.dispose();
    }
}
