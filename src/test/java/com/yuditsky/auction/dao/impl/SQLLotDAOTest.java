package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.LotDAO;
import com.yuditsky.auction.dao.UserDAO;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_DOWN;
import static org.junit.Assert.assertEquals;

public class SQLLotDAOTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final LotDAO lotDAO = new SQLLotDAO();

    Lot testLot = new Lot(4, "Lot", "Desc", "Minsk", 1,
            new BigDecimal("100.2").setScale(4, ROUND_DOWN),
            new BigDecimal("10").setScale(4, ROUND_DOWN));

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool.initPoolData();
    }

    @Test
    public void addLotTest() throws DAOException {
        lotDAO.addLot(testLot);
    }

    @Test
    public void deleteLotByIdTest() throws DAOException {
        lotDAO.deleteLotById(2);
    }

    @Test
    public void findLotByIdTest() throws DAOException {
        Lot expected = testLot;
        Lot actual = lotDAO.findLotById(testLot.getLotId());
        assertEquals(expected, actual);
    }

    @Test
    public void changeDescriptionTest() throws DAOException {
        String expected = "NEW DESC";
        lotDAO.changeDescription(3, expected);
        String actual = lotDAO.findLotById(3).getDescription();
        assertEquals(expected, actual);
    }

    @Test
    public void changeLocationTest() throws DAOException {
        String expected = "NEW LOC";
        lotDAO.changeLocation(3, expected);
        String actual = lotDAO.findLotById(3).getLocation();
        assertEquals(expected, actual);
    }

    @Test
    public void changeStartPriceTest() throws DAOException {
        BigDecimal expected = new BigDecimal("1.34556").setScale(4, ROUND_DOWN);
        lotDAO.changeStartPrice(4, expected);
        BigDecimal actual = lotDAO.findLotById(4).getStartPrice();
        assertEquals(expected, actual);
    }

    @Test
    public void changeMinPriceTest() throws DAOException {
        BigDecimal expected = new BigDecimal("1888.345568").setScale(4, ROUND_DOWN);
        lotDAO.changeMinPrice(4, expected);
        BigDecimal actual = lotDAO.findLotById(4).getMinPrice();
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }
}