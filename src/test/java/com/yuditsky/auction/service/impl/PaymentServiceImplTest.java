package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.entity.Payment;
import com.yuditsky.auction.service.PaymentService;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaymentServiceImplTest {
    private static PaymentService paymentService;

    private static Payment testPayment;
    private static Payment dbPayment;

    @BeforeClass
    public static void init() {
        ServiceFactory factory = ServiceFactory.getInstance();
        paymentService = factory.getPaymentService();

        LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().format(DATA_TIME_FORMATTER), DATA_TIME_FORMATTER);
        testPayment = new Payment(4, 1, new BigDecimal("1.2345"), 1, localDateTime);
        dbPayment = new Payment(1, 1, new BigDecimal("1.1234"), 1, LocalDateTime.parse("2020-01-01 12:12:12", DATA_TIME_FORMATTER));
    }

    @Test
    public void saveTest() throws ServiceException {
        Payment expected = testPayment;

        paymentService.save(expected);

        List<Payment> payments = paymentService.findByPayerId(testPayment.getPayerId());

        assertTrue(payments.contains(testPayment));
    }

    @Test
    public void findByPayerIdTest() throws ServiceException {
        Payment expected = dbPayment;

        Payment actual = paymentService.findByPayerId(expected.getPayerId()).iterator().next();

        assertEquals(expected, actual);
    }
}
