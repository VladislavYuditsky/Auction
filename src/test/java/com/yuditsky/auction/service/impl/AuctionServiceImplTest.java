package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.service.AuctionService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AuctionServiceImplTest {
    private static AuctionService auctionService;

    private static Auction testAuction;
    private static Auction dbAuction;

    @BeforeClass
    public static void init() {
        ServiceFactory factory = ServiceFactory.getInstance();
        auctionService = factory.getAuctionService();

        testAuction = new Auction(4, AuctionType.DIRECT, 1, AuctionStatus.ACTIVE, 0);
        dbAuction = new Auction(1, AuctionType.DIRECT, 1, AuctionStatus.COMPLETED, 1);
    }

    @Test
    public void saveTest() throws ServiceException {
        Auction expected = testAuction;

        auctionService.save(expected);

        Auction actual = auctionService.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws ServiceException {
        Auction expected = dbAuction;

        Auction actual = auctionService.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByStatusTest() throws ServiceException {
        List<Auction> auctions = auctionService.findByStatus(AuctionStatus.COMPLETED);
        assertTrue(auctions.contains(dbAuction));
    }

    @Test
    public void updateTest() throws ServiceException {
        Auction auctionForUpdate = auctionService.findById(3);

        auctionForUpdate.setType(AuctionType.REVERS);
        Auction expected = auctionForUpdate;

        auctionService.update(expected);

        Auction actual = auctionService.findById(3);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws ServiceException {
        Auction auctionForDelete = auctionService.findById(2);

        auctionService.delete(auctionForDelete);

        assertNull(auctionService.findById(2));
    }

    @Test
    public void changeStatusTest() throws ServiceException {
        Auction auctionForUpdate = auctionService.findById(3);

        auctionService.changeStatus(auctionForUpdate);

        auctionForUpdate.setStatus(AuctionStatus.ACTIVE);

        Auction expected = auctionForUpdate;
        Auction actual = auctionService.findById(3);

        assertEquals(expected, actual);
    }
}
