package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.UserDAO;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.UserService;
import com.yuditsky.auction.service.util.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public boolean addUser(User user) throws ServiceException {
        //validator
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        try {
            User userFromDB = userDAO.findByLogin(user.getLogin());
            if (userFromDB != null) {
                return false;
            }

            user.setRole(UserRole.USER);

            Encoder encoder = new Encoder();
            user.setPassword(encoder.encode(user.getPassword()));

            userDAO.save(user);

            return true;
        } catch (DAOException e) {
            logger.error("Can't find user by login", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByLoginAndPassword(String login, String password) throws ServiceException {
        //validator
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        Encoder encoder = new Encoder();

        try {
            User user = userDAO.findByLoginAndPassword(login, encoder.encode(password));
            logger.debug("User " + (user != null ? user.getLogin() + " loaded" : "not found"));
            return user;
        } catch (DAOException e) {
            logger.error("Can't find user by login and password", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        try {
            return userDAO.findAll();
        } catch (DAOException e) {
            logger.error("Can't find all users", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        try {
            return userDAO.findByLogin(login);
        } catch (DAOException e) {
            logger.error("Can't find user by login", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePassword(User user, String password) throws ServiceException {
        //valid
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        Encoder encoder = new Encoder();

        try {
            userDAO.updatePassword(user, encoder.encode(password));
        } catch (DAOException e) {
            logger.error("Can't update password for user " + user.getLogin(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateEmail(User user, String email) throws ServiceException {
        //valid
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        Encoder encoder = new Encoder();

        try {
            userDAO.updateEmail(user, email);
        } catch (DAOException e) {
            logger.error("Can't update email for user " + user.getLogin(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateBalance(User user, BigDecimal sum) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        try{
            userDAO.updateBalance(user, sum);
        } catch (DAOException e) {
        logger.error("Can't update balance for user " + user.getLogin(), e);
        throw new ServiceException(e);
    }
    }

    @Override
    public void addBalance(User user, BigDecimal sum) throws ServiceException{ //
        BigDecimal newBalance = user.getBalance().add(sum);
        updateBalance(user, newBalance);
    }

    @Override
    public void subtractBalance(User user, BigDecimal sum) throws ServiceException{ //
        BigDecimal newBalance = user.getBalance().subtract(sum);
        updateBalance(user, newBalance);
    }

    @Override
    public void deleteUser(User user) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        try {
            userDAO.delete(user);
            logger.debug("User " + user.getLogin() + "deleted");
        } catch (DAOException e) {
            logger.error("Can't delete user " + user.getLogin(), e);
            throw new ServiceException(e);
        }
    }
}
