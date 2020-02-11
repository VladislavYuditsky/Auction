package com.yuditsky.auction.dao.connection;

import org.junit.Test;

import java.sql.Connection;

public class ConnectionPoolTest {
    @Test
    public void initPoolDataTest() throws ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @Test
    public void takeConnectionTest() throws ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        Connection con1 = connectionPool.takeConnection();
        Connection con2 = connectionPool.takeConnection();
        Connection con3 = connectionPool.takeConnection();
        Connection con4 = connectionPool.takeConnection();
        Connection con5 = connectionPool.takeConnection();
        Connection con6 = connectionPool.takeConnection();
    }
}
