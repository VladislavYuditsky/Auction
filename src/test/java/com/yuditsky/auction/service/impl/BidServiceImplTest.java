package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.service.BidService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;
import static org.junit.Assert.*;

public class BidServiceImplTest {
    private static BidService bidService;

    private static Bid testBid;
    private static Bid dbBid;
    private static Bid bidForDelete;

    @BeforeClass
    public static void init() {
        ServiceFactory factory = ServiceFactory.getInstance();
        bidService = factory.getBidService();

        LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().format(DATA_TIME_FORMATTER), DATA_TIME_FORMATTER);
        testBid = new Bid(4, 1, new BigDecimal("5.1234"), localDateTime, 1);
        dbBid = new Bid(1, 1, new BigDecimal("1.2345"),
                LocalDateTime.parse("2020-01-01 12:12:12", DATA_TIME_FORMATTER), 1);
        bidForDelete = new Bid(2, 1, new BigDecimal("5.1234"),
                LocalDateTime.parse("2020-01-01 12:12:12", DATA_TIME_FORMATTER), 1);
    }

    @Test
    public void saveTest() throws ServiceException {
        bidService.save(testBid);

        List<Bid> bids = bidService.findByAuctionId(testBid.getAuctionId());

        assertTrue(bids.contains(testBid));
    }

    @Test
    public void findByAuctionIdTest() throws ServiceException {
        List<Bid> bids = bidService.findByAuctionId(dbBid.getAuctionId());
        assertTrue(bids.contains(dbBid));
    }

    @Test
    public void findWithMinSumByAuctionIdTest() throws ServiceException {
        Bid expected = dbBid;
        Bid actual = bidService.findWithMinSumByAuctionId(dbBid.getAuctionId());

        assertEquals(expected, actual);
    }

    @Test
    public void findMinByBidderIdAndAuctionIdTest() throws ServiceException {
        Bid expected = dbBid;
        Bid actual = bidService.findMinByBidderIdAndAuctionId(dbBid.getAuctionId(), dbBid.getBidderId());

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws ServiceException {
        bidService.save(bidForDelete);
        bidService.delete(bidForDelete);
        List<Bid> bids = bidService.findByAuctionId(1);

        assertFalse(bids.contains(bidForDelete));
    }
}
