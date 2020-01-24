package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.UserDAO;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.math.BigDecimal.ROUND_DOWN;
import static org.junit.Assert.assertEquals;

public class SQLUserDAOTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final UserDAO userDAO = new SQLUserDAO();

    User testUser = new User(2, "qwertry1", "12345", null, "qwe", null, true, new ArrayList<>(), new ArrayList<>());

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool.initPoolData();
    }

    @Test
    public void signUpTest() throws DAOException {
        userDAO.signUp(testUser);
    }

    @Test
    public void signInTest() throws DAOException {
        User expected = testUser;
        User actual = userDAO.signIn(testUser.getLogin(), testUser.getPassword());
        assertEquals(expected, actual);
    }

    @Test
    public void findUserByLoginTest() throws DAOException {
        User expected = testUser;
        User actual = userDAO.findUserByLogin(testUser.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    public void changePasswordTest() throws DAOException {
        String expected = "5432111";
        userDAO.changePassword(testUser.getLogin(), expected);
        String actual = userDAO.findUserByLogin(testUser.getLogin()).getPassword();
        assertEquals(expected, actual);
    }

    @Test
    public void changeEmailTest() throws DAOException {
        String expected = "123@gmail.com";
        userDAO.changeEmail(testUser.getLogin(), expected);
        String actual = userDAO.findUserByLogin(testUser.getLogin()).getEmail();
        assertEquals(expected, actual);
    }

    //setScale(3, ROUND_DOWN)

    @Test
    public void changeBalanceTest() throws DAOException {
        BigDecimal expected = new BigDecimal("123.99987").setScale(4, ROUND_DOWN);
        userDAO.changeBalance(testUser.getLogin(), expected);
        BigDecimal actual = userDAO.findUserByLogin(testUser.getLogin()).getBalance();
        assertEquals(expected, actual);
    }

    @Test
    public void changeRoleTest() throws DAOException {
        UserRole expected = UserRole.USER;
        userDAO.changeRole(testUser.getLogin(), expected);
        UserRole actual = userDAO.findUserByLogin(testUser.getLogin()).getRole();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUserTest() throws DAOException {
        userDAO.deleteUser(testUser.getLogin());
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }
}
