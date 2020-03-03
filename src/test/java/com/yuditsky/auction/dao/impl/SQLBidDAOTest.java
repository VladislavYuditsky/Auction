package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.BidDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.Bid;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;
import static org.junit.Assert.*;

public class SQLBidDAOTest {
    private static BidDAO bidDAO;

    private static Bid testBid;
    private static Bid dbBid;

    @BeforeClass
    public static void init() {
        DAOFactory factory = DAOFactory.getInstance();
        bidDAO = factory.getBidDAO();

        LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().format(DATA_TIME_FORMATTER), DATA_TIME_FORMATTER);
        testBid = new Bid(4, 1, new BigDecimal("5.4321"), localDateTime, 1);
        dbBid = new Bid(1, 1, new BigDecimal("1.2345"),
                LocalDateTime.parse("2020-01-01 12:12:12", DATA_TIME_FORMATTER), 1);
    }

    @Test
    public void saveTest() throws DAOException {
        bidDAO.save(testBid);

        Bid expected = testBid;
        Bid actual = bidDAO.findById(testBid.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws DAOException {
        Bid expected = dbBid;
        Bid actual = bidDAO.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() throws DAOException {
        List<Bid> bids = bidDAO.findAll();
        assertTrue(bids.contains(dbBid));
    }

    @Test
    public void updateTest() throws DAOException {
        BigDecimal sum = new BigDecimal("1.4321");
        Bid bidForUpdate = bidDAO.findById(3);

        bidForUpdate.setSum(sum);
        Bid expected = bidForUpdate;

        bidDAO.update(expected);

        Bid actual = bidDAO.findById(3);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws DAOException {
        Bid bidForDelete = bidDAO.findById(2);

        bidDAO.delete(bidForDelete);

        Bid bid = bidDAO.findById(2);

        assertNull(bid);
    }

    @Test
    public void findByAuctionIdTest() throws DAOException {
        List<Bid> bids = bidDAO.findByAuctionId(1);
        assertTrue(bids.contains(dbBid));
    }

    @Test
    public void findWithMinSumByBidderIdAndAuctionIdTest() throws DAOException {
        Bid expected = bidDAO.findById(1);
        Bid actual = bidDAO.findWithMinSumByBidderIdAndAuctionId(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void findWithMinSumByAuctionIdTest() throws DAOException {
        Bid expected = bidDAO.findById(1);
        Bid actual = bidDAO.findWithMinSumByAuctionId(1);

        assertEquals(expected, actual);
    }
}
