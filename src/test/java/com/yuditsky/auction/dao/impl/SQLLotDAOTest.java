package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.LotDAO;
import com.yuditsky.auction.dao.PaymentDAO;
import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.Payment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;
import static org.junit.Assert.*;

public class SQLLotDAOTest {
    private static LotDAO lotDAO;

    private static Lot testLot;
    private static Lot dbLot;

    @BeforeClass
    public static void init() {
        DAOFactory factory = DAOFactory.getInstance();
        lotDAO = factory.getLotDAO();

        testLot = new Lot(4, "lotName", "description", "location", 1,
                new BigDecimal("1.2345"));

        dbLot = new Lot(1, "test", "test", "test", 1,
                new BigDecimal("1.2345"));
    }

    @Test
    public void saveTest() throws DAOException {
        lotDAO.save(testLot);

        Lot expected = testLot;
        Lot actual = lotDAO.findById(testLot.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws DAOException {
        Lot expected = dbLot;
        Lot actual = lotDAO.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() throws DAOException {
        List<Lot> lots = lotDAO.findAll();
        assertTrue(lots.contains(dbLot));
    }

    @Test
    public void findByOwnerIdTest() throws DAOException {
        List<Lot> lots = lotDAO.findByOwnerId(1);
        assertTrue(lots.contains(dbLot));
    }

    @Test
    public void updateTest() throws DAOException {
        BigDecimal newPrice = new BigDecimal("1.1111");
        Lot lotForUpdate = lotDAO.findById(3);

        lotForUpdate.setStartPrice(newPrice);
        Lot expected = lotForUpdate;

        lotDAO.update(expected);

        Lot actual = lotDAO.findById(3);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws DAOException {
        Lot lotForDelete = lotDAO.findById(2);

        lotDAO.delete(lotForDelete);

        Lot lot = lotDAO.findById(2);

        assertNull(lot);
    }
}
