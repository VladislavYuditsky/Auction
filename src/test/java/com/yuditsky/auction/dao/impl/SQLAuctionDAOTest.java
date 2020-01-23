package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.AuctionDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SQLAuctionDAOTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final AuctionDAO auctionDAO = new SQLAuctionDAO();

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool.initPoolData();
    }

    @Test
    public void addLotTest() throws DAOException {
        //bidDAO.addBid(testLot);
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }
}
