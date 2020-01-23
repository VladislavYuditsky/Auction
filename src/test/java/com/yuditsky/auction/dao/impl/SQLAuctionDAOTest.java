package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.AuctionDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.yuditsky.auction.Const.DATA_TIME_FORMATTER;
import static org.junit.Assert.assertEquals;

public class SQLAuctionDAOTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final AuctionDAO auctionDAO = new SQLAuctionDAO();

    private Auction testAuction = new Auction(AuctionType.DIRECT, 1, LocalDateTime.now());

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool.initPoolData();
    }

    @Test
    public void addAuctionTest() throws DAOException {
        auctionDAO.addAuction(testAuction);
    }

    @Test
    public void findAuctionByIdTest() throws DAOException {
        Auction expected = new Auction(3, AuctionType.DIRECT, 1, new ArrayList<>(),
                LocalDateTime.parse("2020-01-24 01:15:32", DATA_TIME_FORMATTER));
        Auction actual = auctionDAO.findAuctionById(3);
        assertEquals(expected, actual);
    }

    @Test
    public void changeAuctionTypeTest() throws DAOException {
        AuctionType expected = AuctionType.REVERS;
        auctionDAO.changeAuctionType(3, expected);
        AuctionType actual = auctionDAO.findAuctionById(3).getType();
        assertEquals(expected, actual);
    }

    @Test
    public void changeLotIdTest() throws DAOException {
        int expected = 5;
        auctionDAO.changeLotId(3, expected);
        int actual = auctionDAO.findAuctionById(3).getLotId();
        assertEquals(expected, actual);
    }

    @Test
    public void changeFinishTimeTest() throws DAOException {
        LocalDateTime expected = LocalDateTime.now();
        auctionDAO.changeFinishTime(3, expected);
        LocalDateTime actual = auctionDAO.findAuctionById(3).getFinishTime();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteAuctionByIdTest() throws DAOException {
        auctionDAO.deleteAuction(1);
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }
}
