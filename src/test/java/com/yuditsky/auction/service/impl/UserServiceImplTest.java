package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    private static UserService userService;

    private User testUser = new User(4, "service", "Test1", UserRole.USER, "test1@gmail.com",
            new BigDecimal("0.0000"), false);

    private User dbUser = new User(1, "test", "Test0", UserRole.USER, "test@mail.ru",
            new BigDecimal("0.0000"), false);

    @BeforeClass
    public static void init() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Test
    public void saveTest() throws ServiceException {
        User expected = testUser;

        userService.save(expected);

        User actual = userService.findById(testUser.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void saveUserThatAlreadyExistsTest() throws ServiceException {
        boolean actual = userService.save(dbUser);
        assertFalse(actual);
    }

    @Test
    public void findByLoginTest() throws ServiceException {
        User actual = userService.findByLogin(dbUser.getLogin());
        User expected = dbUser;

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws ServiceException {
        User actual = userService.findById(dbUser.getId());
        User expected = dbUser;

        assertEquals(expected, actual);
    }

    @Test
    public void updateSettingsTest() throws ServiceException{
        User userForUpdate = userService.findById(3);

        String expected = "new_email@gmail.com";

        userService.updateSettings(userForUpdate, expected, null);

        String actual = userService.findByLogin(userForUpdate.getLogin()).getEmail();

        assertEquals(expected, actual);
    }

    @Test
    public void subtractBalanceTest() throws ServiceException {
        User userForUpdate = userService.findById(3);

        BigDecimal subSum = new BigDecimal("1");
        BigDecimal expected = userForUpdate.getBalance().subtract(subSum);
        userService.subtractBalance(userForUpdate, subSum);
        BigDecimal actual = userService.findByLogin(userForUpdate.getLogin()).getBalance();
        assertEquals(expected, actual);
    }
    @Test
    public void addBalanceTest() throws ServiceException {
        User userForUpdate = userService.findById(3);

        BigDecimal addSum = new BigDecimal("3");
        BigDecimal expected = userForUpdate.getBalance().add(addSum);
        userService.addBalance(userForUpdate, addSum);
        BigDecimal actual = userService.findByLogin(userForUpdate.getLogin()).getBalance();
        assertEquals(expected, actual);
    }

    @Test
    public void changeBlockStatusTest() throws ServiceException {
        User userForUpdate = userService.findById(3);

        userService.changeBlockStatus(userForUpdate);
        boolean actual = userService.findByLogin(userForUpdate.getLogin()).isBlocked();

        assertTrue(actual);
    }

    @Test
    public void deleteTest() throws ServiceException {
        User userForDelete = userService.findById(2);

        userService.delete(userForDelete);

        User actual = userService.findByLogin(userForDelete.getLogin());

        assertNull(actual);
    }
}
