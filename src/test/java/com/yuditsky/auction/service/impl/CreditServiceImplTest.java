package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.entity.Credit;
import com.yuditsky.auction.service.CreditService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;
import static org.junit.Assert.*;

public class CreditServiceImplTest {
    private static CreditService creditService;

    private static Credit testCredit;
    private static Credit dbCredit;

    @BeforeClass
    public static void init() {
        ServiceFactory factory = ServiceFactory.getInstance();
        creditService = factory.getCreditService();

        LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().plusYears(1).format(DATA_TIME_FORMATTER), DATA_TIME_FORMATTER);
        BigDecimal sum = new BigDecimal("1.1234");
        testCredit = new Credit(4, sum, localDateTime, sum, sum, 1);
        dbCredit = new Credit(1, sum, LocalDateTime.parse("2020-01-01 12:12:12", DATA_TIME_FORMATTER), sum, sum, 1);
    }

    @Test
    public void saveTest() throws ServiceException {
        Credit expected = testCredit;

        creditService.save(expected);

        Credit actual = creditService.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws ServiceException {
        Credit expected = dbCredit;

        Credit actual = creditService.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findByBorrowerIdTest() throws ServiceException {
        List<Credit> lots = creditService.findByBorrowerId(dbCredit.getBorrowerId());
        assertTrue(lots.contains(dbCredit));
    }

    @Test
    public void updateBalanceTest() throws ServiceException {
        Credit creditForUpdate = creditService.findById(3);
        BigDecimal expected = new BigDecimal("5.4321");

        creditService.updateBalance(creditForUpdate, expected);

        BigDecimal actual = creditService.findById(3).getBalance();

        assertEquals(expected, actual);
    }

    @Test
    public void findDebtorsTest() throws ServiceException {
        List<Integer> debtors = creditService.findDebtors();
        assertTrue(debtors.contains(1));
    }
}
