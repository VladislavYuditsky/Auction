package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.PaymentDAO;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Payment;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_DOWN;
import static org.junit.Assert.assertEquals;

public class SQLPaymentDAOTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final PaymentDAO paymentDAO = new SQLPaymentDAO();

    private Payment testPayment = new Payment(1, 1,
            new BigDecimal(1.1234).setScale(4, ROUND_DOWN), 1, LocalDateTime.now());

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool.initPoolData();
    }

    @Test
    public void addPaymentTest() throws DAOException {
        paymentDAO.addPayment(testPayment);
    }

    @Test
    public void findPaymentByIdTest() throws DAOException {
        Payment expected = testPayment;
        Payment actual = paymentDAO.findPaymentById(testPayment.getPaymentId());
        assertEquals(expected, actual);
    }

    @Test
    public void findPaymentIdsByPayerIdTest() throws DAOException {
        List<Integer> expected = new ArrayList<>();
        List<Integer> actual = paymentDAO.findPaymentIdsByPayerId(2);
        assertEquals(expected, actual);
    }

    @Test
    public void changePayerIdTest() throws DAOException {
        int expected = 2;
        paymentDAO.changePayerId(testPayment, expected);
        int actual = paymentDAO.findPaymentById(1).getPayerId();
        assertEquals(expected, actual);
    }

    @Test
    public void changeSumTest() throws DAOException {
        BigDecimal expected = new BigDecimal(23.23232).setScale(4, ROUND_DOWN);
        paymentDAO.changeSum(testPayment, expected);
        BigDecimal actual = paymentDAO.findPaymentById(1).getSum();
        assertEquals(expected, actual);
    }

    @Test
    public void changeLotIdTest() throws DAOException {
        int expected = 2;
        paymentDAO.changeLotId(testPayment, expected);
        int actual = paymentDAO.findPaymentById(1).getLotId();
        assertEquals(expected, actual);
    }

    @Test
    public void changeDateTest() throws DAOException {
        LocalDateTime expected = LocalDateTime.now();
        paymentDAO.changeDate(testPayment, expected);
        LocalDateTime actual = paymentDAO.findPaymentById(1).getDate();
        assertEquals(expected, actual);
    }

    @Test
    public void deletePaymentByIdTest() throws DAOException {
        paymentDAO.deletePayment(testPayment);
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }
}
