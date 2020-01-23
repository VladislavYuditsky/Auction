package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.BidDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Bid;
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

    @Test
    public void changeBidderIdTest() throws DAOException {
        int expected = 2;
        bidDAO.changeBidderId(1, expected);
        int actual = bidDAO.findBidById(1).getBidderId();
        assertEquals(expected, actual);
    }

    @Test
    public void changeSumTest() throws DAOException {
        BigDecimal expected = new BigDecimal("100.2").setScale(4, ROUND_DOWN);
        bidDAO.changeSum(1, expected);
        BigDecimal actual = bidDAO.findBidById(1).getSum();
        assertEquals(expected, actual);
    }

    @Test
    public void changeTimeTest() throws DAOException {
        LocalDateTime expected = LocalDateTime.now();
        bidDAO.changeTime(1, expected);
        LocalDateTime actual = bidDAO.findBidById(1).getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void changeAuctionIdTest() throws DAOException {
        int expected = 2;
        bidDAO.changeAuctionId(1, expected);
        int actual = bidDAO.findBidById(1).getAuctionId();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteLotByIdTest() throws DAOException {
        bidDAO.deleteBidById(1);
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }
}
