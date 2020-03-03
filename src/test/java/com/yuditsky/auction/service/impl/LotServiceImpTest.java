package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.service.LotService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class LotServiceImpTest {
    private static LotService lotService;

    private static Lot testLot;
    private static Lot dbLot;

    @BeforeClass
    public static void init() {
        ServiceFactory factory = ServiceFactory.getInstance();
        lotService = factory.getLotService();

        testLot = new Lot(4, "lotName", "description", "location", 1,
                new BigDecimal("1.2345"));

        dbLot = new Lot(1, "test", "test", "test", 1,
                new BigDecimal("1.2345"));
    }

    @Test
    public void saveTest() throws ServiceException {
        Lot expected = testLot;

        lotService.save(expected);

        Lot actual = lotService.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws ServiceException {
        Lot expected = dbLot;

        Lot actual = lotService.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByOwnerIdTest() throws ServiceException {
        List<Lot> lots = lotService.findByOwnerId(dbLot.getOwnerId());
        assertTrue(lots.contains(dbLot));
    }

    @Test
    public void updateTest() throws ServiceException {
        Lot lotForUpdate = lotService.findById(3);

        String expected = "Minsk";
        lotForUpdate.setLocation(expected);

        lotService.update(lotForUpdate);

        String actual = lotService.findById(3).getLocation();

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws ServiceException {
        Lot lotForDelete = lotService.findById(2);

        lotService.delete(lotForDelete);

        assertNull(lotService.findById(2));
    }
}
