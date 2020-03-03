package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.UserDAO;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class SQLUserDAOTest {
    private static UserDAO userDAO;

    private User testUser = new User(4, "test1", "Test1", UserRole.USER, "test1@gmail.com",
            new BigDecimal("0.0000"), false);

    private User dbUser = new User(1, "test", "Test0", UserRole.USER, "test@mail.ru",
            new BigDecimal("0.0000"), false);

    @BeforeClass
    public static void init() {
        DAOFactory factory = DAOFactory.getInstance();
        userDAO = factory.getUserDAO();
    }

    @Test
    public void saveTest() throws DAOException {
        userDAO.save(testUser);

        User expected = testUser;
        User actual = userDAO.findById(testUser.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTest() throws DAOException {
        User expected = dbUser;
        User actual = userDAO.findById(dbUser.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() throws DAOException {
        List<User> users = userDAO.findAll();
        assertTrue(users.contains(dbUser));
    }

    @Test
    public void updateTest() throws DAOException {
        User userForUpdate = userDAO.findById(3);

        userForUpdate.setPassword("Pass1");

        User expected = userForUpdate;

        userDAO.update(expected);

        User actual = userDAO.findById(3);

        assertEquals(expected, actual);
    }

    @Test
    public void findByLoginTest() throws DAOException {
        User expected = dbUser;

        User actual = userDAO.findByLogin(dbUser.getLogin());

        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws DAOException {
        User userForDelete = userDAO.findById(2);

        userDAO.delete(userForDelete);

        User user = userDAO.findById(2);

        assertNull(user);
    }
}
