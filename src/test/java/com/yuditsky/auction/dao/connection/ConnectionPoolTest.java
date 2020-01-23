package com.yuditsky.auction.dao.connection;

import org.junit.Test;

public class ConnectionPoolTest {
    @Test
    public void initPoolDataTest() throws ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }
}
