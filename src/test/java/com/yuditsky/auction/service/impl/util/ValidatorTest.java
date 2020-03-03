package com.yuditsky.auction.service.impl.util;

import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {
    private Validator validator = new Validator();

    @Test
    public void checkLoginTrueTest() {
        String validLogin = "Login";
        boolean actual = validator.checkLogin(validLogin);
        assertTrue(actual);
    }

    @Test
    public void checkLoginFalseTest() {
        String invalidLogin = "Invalid.Login";
        boolean actual = validator.checkLogin(invalidLogin);
        assertFalse(actual);
    }

    @Test
    public void checkPasswordTrueTest() {
        String validPassword = "Valid1";
        boolean actual = validator.checkPassword(validPassword);
        assertTrue(actual);
    }

    @Test
    public void checkPasswordFalseTest() {
        String invalidPassword = "inv";
        boolean actual = validator.checkPassword(invalidPassword);
        assertFalse(actual);
    }

    @Test
    public void checkTextTrueTest() {
        String validText = "Valid";
        boolean actual = validator.checkText(validText);
        assertTrue(actual);
    }

    @Test
    public void checkTextFalseTest() {
        String invalidText = "";
        boolean actual = validator.checkText(invalidText);
        assertFalse(actual);
    }

    @Test
    public void checkEmailTrueTest() {
        String validEmail = "valid@gmail.com";
        boolean actual = validator.checkText(validEmail);
        assertTrue(actual);
    }

    @Test
    public void checkEmailFalseTest() {
        String invalidEmail = "invalid";
        boolean actual = validator.checkEmail(invalidEmail);
        assertFalse(actual);
    }

    @Test
    public void checkUserTrueTest() {
        User validUser = new User("Valid", "Valid0", "valid@gmail.com");
        boolean actual = validator.checkUser(validUser);
        assertTrue(actual);
    }

    @Test
    public void checkUserFalseTest() {
        boolean actual = validator.checkUser(null);
        assertFalse(actual);
    }

    @Test
    public void checkBidTrueTest() {
        Bid validBid = new Bid(1, new BigDecimal("1.1234"), LocalDateTime.now(), 1);
        boolean actual = validator.checkBid(validBid);
        assertTrue(actual);
    }

    @Test
    public void checkBidFalseTest() {
        boolean actual = validator.checkBid(null);
        assertFalse(actual);
    }

    @Test
    public void checkLotTrueTest() {
        Lot validLot = new Lot("name", "description", "location", 1,
                new BigDecimal("1.1234"));
        boolean actual = validator.checkLot(validLot);
        assertTrue(actual);
    }

    @Test
    public void checkLotFalseTest() {
        boolean actual = validator.checkLot(null);
        assertFalse(actual);
    }

    @Test
    public void checkBigDecimalTrueTest() {
        BigDecimal valid = new BigDecimal("1");
        boolean actual = validator.checkBigDecimal(valid);
        assertTrue(actual);
    }

    @Test
    public void checkBigDecimalFalseTest() {
        boolean actual = validator.checkLot(null);
        assertFalse(actual);
    }
}
