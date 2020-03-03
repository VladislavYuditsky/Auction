package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.CreditDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.entity.Credit;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;
import static org.junit.Assert.*;

public class SQLCreditDAOTest {
    private static CreditDAO creditDAO;

    private static Credit testCredit;
    private static Credit dbCredit;

    @BeforeClass
    public static void init() {
        DAOFactory factory = DAOFactory.getInstance();
        creditDAO = factory.getCreditDAO();

        LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().format(DATA_TIME_FORMATTER), DATA_TIME_FORMATTER);
        BigDecimal sum = new BigDecimal("1.1234");
        testCredit = new Credit(4, sum, localDateTime, sum, sum, 1);
        dbCredit = new Credit(1, sum, LocalDateTime.parse("2020-01-01 12:12:12", DATA_TIME_FORMATTER), sum, sum, 1);
    }

    @Test
    public void saveTest() throws DAOException {
        creditDAO.save(testCredit);

        Credit expected = testCredit;
        Credit actual = creditDAO.findById(testCredit.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws DAOException {
        Credit expected = dbCredit;
        Credit actual = creditDAO.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() throws DAOException {
        List<Credit> credits = creditDAO.findAll();
        assertTrue(credits.contains(dbCredit));
    }

    @Test
    public void updateTest() throws DAOException {
        BigDecimal sum = new BigDecimal("1.2345");
        Credit creditForUpdate = creditDAO.findById(3);

        creditForUpdate.setSum(sum);
        Credit expected = creditForUpdate;

        creditDAO.update(expected);

        Credit actual = creditDAO.findById(3);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws DAOException {
        Credit creditForDelete = creditDAO.findById(2);

        creditDAO.delete(creditForDelete);

        Credit credit = creditDAO.findById(2);

        assertNull(credit);
    }

    @Test
    public void findByBorrowerIdTest() throws DAOException {
        List<Credit> credits = creditDAO.findByBorrowerId(1);
        assertTrue(credits.contains(dbCredit));
    }

    @Test
    public void findDebtorsTest() throws DAOException {
        List<Integer> debtors = creditDAO.findDebtors();
        assertTrue(debtors.contains(1));
    }
}
