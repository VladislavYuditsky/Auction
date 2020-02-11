package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.ServiceFactory;
import com.yuditsky.auction.service.UserService;
import com.yuditsky.auction.service.util.Encoder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_DOWN;
import static org.junit.Assert.assertEquals;

public class UserServiceImplTest {
    private static UserService userService;
    private User testUser = new User(1, "loggg", "passss", UserRole.USER, "mail",
            new BigDecimal(1).setScale(4, ROUND_DOWN), false, new ArrayList<>(),
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    @BeforeClass
    public static void init() {
        ServiceFactory factory = ServiceFactory.getInstance();
        userService = factory.getUserService();
    }

    @Test
    public void addUserTest() throws ServiceException {
        userService.save(testUser);
    }

    @Test
    public void loadUserByLoginAndPasswordTest() throws ServiceException {
        User expected = testUser;
        User actual = userService.findByLoginAndPassword(testUser.getLogin(), testUser.getPassword());
        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() throws ServiceException {
        List<User> users = userService.findAll();
        //System.out.println(users);
    }

    /*@Test
    public void updatePasswordTest() throws ServiceException {
        String newPass = "1234";
        userService.updatePassword(testUser, newPass);
        Encoder encoder = new Encoder();
        String expected = encoder.encode(newPass);
        String actual = userService.findByLogin(testUser.getLogin()).getPassword();
        assertEquals(expected, actual);
    }*/

    @Test
    public void deleteUserTest() throws ServiceException {
        userService.delete(testUser);
    }

    @Test
    public void subtractBalanceTest() throws ServiceException {
        BigDecimal subSum = new BigDecimal("1").setScale(4,ROUND_DOWN);
        BigDecimal expected = testUser.getBalance().subtract(subSum);
        userService.subtractBalance(testUser, subSum);
        BigDecimal actual = userService.findByLogin(testUser.getLogin()).getBalance();
        assertEquals(expected, actual);
    }
    @Test
    public void addBalanceTest() throws ServiceException {
        BigDecimal addSum = new BigDecimal("3");
        BigDecimal expected = testUser.getBalance().add(addSum);
        userService.addBalance(testUser, addSum);
        BigDecimal actual = userService.findByLogin(testUser.getLogin()).getBalance();
        assertEquals(expected, actual);
    }
}
