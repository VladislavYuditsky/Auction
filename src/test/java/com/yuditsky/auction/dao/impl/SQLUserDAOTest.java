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
    /*private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final UserDAO userDAO = new SQLUserDAO();

    User testUser = new User(2, "qwertry3", "12345", null, "qwe", null,
            true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool.initPoolData();
    }

    @Test
    public void signUpTest() throws DAOException {
        userDAO.save(testUser);
    }

    @Test
    public void signInTest() throws DAOException {
        User expected = testUser;
        User actual = userDAO.findByLoginAndPassword(testUser.getLogin(), testUser.getPassword());
        assertEquals(expected, actual);
    }

    @Test
    public void findUserByLoginTest() throws DAOException {
        User expected = testUser;
        User actual = userDAO.findByLogin(testUser.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    public void changePasswordTest() throws DAOException {
        String expected = "5432111";
        userDAO.updatePassword(testUser, expected);
        String actual = userDAO.findByLogin(testUser.getLogin()).getPassword();
        assertEquals(expected, actual);
    }

    @Test
    public void changeEmailTest() throws DAOException {
        String expected = "123@gmail.com";
        userDAO.updateEmail(testUser, expected);
        String actual = userDAO.findByLogin(testUser.getLogin()).getEmail();
        assertEquals(expected, actual);
    }

    //setScale(3, ROUND_DOWN)

    @Test
    public void changeBalanceTest() throws DAOException {
        BigDecimal expected = new BigDecimal("123.99987").setScale(4, ROUND_DOWN);
        userDAO.updateBalance(testUser, expected);
        BigDecimal actual = userDAO.findByLogin(testUser.getLogin()).getBalance();
        assertEquals(expected, actual);
    }

    @Test
    public void changeRoleTest() throws DAOException {
        UserRole expected = UserRole.USER;
        userDAO.updateRole(testUser, expected);
        UserRole actual = userDAO.findByLogin(testUser.getLogin()).getRole();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUserTest() throws DAOException {
        userDAO.delete(testUser);
    }

    @AfterClass
    public static void dispose() {
        connectionPool.dispose();
    }*/
}
