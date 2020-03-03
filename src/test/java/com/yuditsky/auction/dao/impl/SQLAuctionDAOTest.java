package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.AuctionDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SQLAuctionDAOTest {
    private static AuctionDAO auctionDAO;

    private static Auction testAuction;
    private static Auction dbAuction;

    @BeforeClass
    public static void init() {
        DAOFactory factory = DAOFactory.getInstance();
        auctionDAO = factory.getAuctionDAO();

        testAuction = new Auction(4, AuctionType.DIRECT, 1, AuctionStatus.ACTIVE, 0);
        dbAuction = new Auction(1, AuctionType.DIRECT, 1, AuctionStatus.COMPLETED, 1);
    }

    @Test
    public void saveTest() throws DAOException {
        auctionDAO.save(testAuction);

        Auction expected = testAuction;
        Auction actual = auctionDAO.findById(testAuction.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws DAOException {
        Auction expected = dbAuction;
        Auction actual = auctionDAO.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() throws DAOException {
        List<Auction> actions = auctionDAO.findAll();
        assertTrue(actions.contains(dbAuction));
    }

    @Test
    public void updateTest() throws DAOException {
        Auction auctionForUpdate = auctionDAO.findById(3);

        auctionForUpdate.setType(AuctionType.REVERS);
        Auction expected = auctionForUpdate;

        auctionDAO.update(expected);

        Auction actual = auctionDAO.findById(3);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws DAOException {
        Auction auctionForDelete = auctionDAO.findById(2);

        auctionDAO.delete(auctionForDelete);

        Auction auction = auctionDAO.findById(2);

        assertNull(auction);
    }

    @Test
    public void findByStatusTest() throws DAOException {
        List<Auction> actions = auctionDAO.findByStatus(AuctionStatus.COMPLETED);

        assertTrue(actions.contains(dbAuction));
    }
}
