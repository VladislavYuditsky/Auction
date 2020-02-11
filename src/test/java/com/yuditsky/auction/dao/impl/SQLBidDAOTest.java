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
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_DOWN;
import static org.junit.Assert.assertEquals;

public class SQLBidDAOTest {
   /* private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final BidDAO bidDAO = new SQLBidDAO();

    Bid testBid = new Bid(7, 1, new BigDecimal("2.345").setScale(4, ROUND_DOWN),
            LocalDateTime.now(), 1);

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool.initPoolData();
    }

    @Test
    public void addBidTest() throws DAOException {
        bidDAO.save(testBid);
    }

    @Test
    public void findBidByIdTest() throws DAOException {
        Bid expected = testBid;
        Bid actual = bidDAO.findById(testBid.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void findBidIdsByAuctionIdTest() throws DAOException {
        List<Integer> expected = new ArrayList<>();
        List<Integer> actual = bidDAO.findByAuctionId(1);
        assertEquals(expected, actual);
    }

    @Test
    public void findBidIdsByBidderIdTest() throws DAOException {
        List<Integer> expected = new ArrayList<>();
        expected.add(7);
        List<Integer> actual = bidDAO.findByBidderId(1);
        assertEquals(expected, actual);
    }

    @Test
    public void changeBidderIdTest() throws DAOException {
        int expected = 2;
        bidDAO.updateBidderId(testBid, expected);
        int actual = bidDAO.findById(1).getBidderId();
        assertEquals(expected, actual);
    }

    @Test
    public void changeSumTest() throws DAOException {
        BigDecimal expected = new BigDecimal("100.2").setScale(4, ROUND_DOWN);
        bidDAO.updateSum(testBid, expected);
        BigDecimal actual = bidDAO.findById(1).getSum();
        assertEquals(expected, actual);
    }

    @Test
    public void changeTimeTest() throws DAOException {
        LocalDateTime expected = LocalDateTime.now();
        bidDAO.updateTime(testBid, expected);
        LocalDateTime actual = bidDAO.findById(1).getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void changeAuctionIdTest() throws DAOException {
        int expected = 2;
        bidDAO.updateAuctionId(testBid, expected);
        int actual = bidDAO.findById(1).getAuctionId();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteLotByIdTest() throws DAOException {
        bidDAO.delete(testBid);
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }*/
}
