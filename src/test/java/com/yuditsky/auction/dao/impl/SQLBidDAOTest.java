package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.BidDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ROUND_DOWN;
import static org.junit.Assert.assertEquals;

public class SQLBidDAOTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final BidDAO bidDAO = new SQLBidDAO();

    Bid testBid = new Bid(1, 1, new BigDecimal("2.345").setScale(4, ROUND_DOWN),
            LocalDateTime.now(), 1);

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool.initPoolData();
    }

    @Test
    public void addBidTest() throws DAOException {
        bidDAO.addBid(testBid);
    }

    @Test
    public void findLotByIdTest() throws DAOException {
        Bid expected = testBid;
        Bid actual = bidDAO.findBidById(testBid.getBidId());
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }
}
