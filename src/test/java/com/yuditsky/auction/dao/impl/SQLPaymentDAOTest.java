package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.PaymentDAO;
import com.yuditsky.auction.entity.Payment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;
import static org.junit.Assert.*;

public class SQLPaymentDAOTest {
    private static PaymentDAO paymentDAO;

    private static Payment testPayment;
    private static Payment dbPayment;

    @BeforeClass
    public static void init() {
        DAOFactory factory = DAOFactory.getInstance();
        paymentDAO = factory.getPaymentDAO();

        LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().format(DATA_TIME_FORMATTER),
                DATA_TIME_FORMATTER);
        testPayment = new Payment(4, 1, new BigDecimal("1.2345"), 1, localDateTime);

        dbPayment = new Payment(1, 1, new BigDecimal("1.1234"), 1,
                LocalDateTime.parse("2020-01-01 12:12:12", DATA_TIME_FORMATTER));
    }

    @Test
    public void saveTest() throws DAOException {
        paymentDAO.save(testPayment);

        Payment expected = testPayment;
        Payment actual = paymentDAO.findById(testPayment.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws DAOException {
        Payment expected = dbPayment;
        Payment actual = paymentDAO.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() throws DAOException {
        List<Payment> payments = paymentDAO.findAll();
        assertTrue(payments.contains(dbPayment));
    }

    @Test
    public void findByPayerIdTest() throws DAOException {
        List<Payment> payments = paymentDAO.findByPayerId(1);
        assertTrue(payments.contains(dbPayment));
    }

    @Test
    public void updateTest() throws DAOException {
        BigDecimal newSum = new BigDecimal("1.1111");
        Payment paymentForUpdate = paymentDAO.findById(3);

        paymentForUpdate.setSum(newSum);
        Payment expected = paymentForUpdate;

        paymentDAO.update(expected);

        Payment actual = paymentDAO.findById(3);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws DAOException {
        Payment paymentForDelete = paymentDAO.findById(2);

        paymentDAO.delete(paymentForDelete);

        Payment payment = paymentDAO.findById(2);

        assertNull(payment);
    }
}
