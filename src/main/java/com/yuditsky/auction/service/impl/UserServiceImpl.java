package com.yuditsky.auction.service.impl;

import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.UserDAO;
import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;
import com.yuditsky.auction.service.ServiceException;
import com.yuditsky.auction.service.UserService;
import com.yuditsky.auction.service.util.Encoder;
import com.yuditsky.auction.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.security.Provider;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;
    private final Encoder encoder;
    private final Validator validator;

    public UserServiceImpl() {
        DAOFactory factory = DAOFactory.getInstance();
        userDAO = factory.getUserDAO();

        validator = new Validator();
        encoder = new Encoder();
    }

    @Override
    public boolean save(User user) throws ServiceException {
        try {
            if(!validator.checkUser(user)){
                throw new ServiceException("Invalid user data");
            }

            User userFromDB = userDAO.findByLogin(user.getLogin());
            if (userFromDB != null) {
                return false;
            }

            user.setRole(UserRole.USER);

            user.setPassword(encoder.encode(user.getPassword()));

            userDAO.save(user);

            return true;
        } catch (DAOException e) {
            logger.error("Can't find user by login", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            User user = userDAO.findByLogin(login);

            if(!encoder.checkPassword(password, user.getPassword())){
                user = null;
            }

            logger.debug("User " + (user != null ? user.getLogin() + " loaded" : "not found"));
            return user;
        } catch (DAOException | IllegalArgumentException e) {
            logger.error("Can't find user by login and password", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDAO.findAll();
        } catch (DAOException e) {
            logger.error("Can't find all users", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User findByLogin(String login) throws ServiceException {
        try {
            return userDAO.findByLogin(login);
        } catch (DAOException e) {
            logger.error("Can't find user by login", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User findById(int id) throws ServiceException {
        try {
            return userDAO.findById(id);
        } catch (DAOException e) {
            logger.error("Can't find user by id=" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            logger.error("Can't update user " + user.getLogin(), e);
            throw new ServiceException(e);
        }
    }

    /*@Override
    public void updatePassword(User user, String password) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        Encoder encoder = new Encoder();

        try {
            user.setPassword(encoder.encode(password)); // v contr
            userDAO.update(user);
            //userDAO.updatePassword(user, encoder.encode(password));
        } catch (DAOException e) {
            logger.error("Can't update password for user " + user.getLogin(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateEmail(User user, String email) throws ServiceException {
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

        try {
            userDAO.updateBalance(user, sum);
        } catch (DAOException e) {
            logger.error("Can't update balance for user " + user.getLogin(), e);
            throw new ServiceException(e);
        }
    }*/

    @Override
    public void addBalance(User user, BigDecimal sum) throws ServiceException { //
        if(!validator.checkSum(sum)){
            throw new ServiceException("Invalid sum data");
        }

        BigDecimal newBalance = user.getBalance().add(sum);
        user.setBalance(newBalance);
        update(user);
        //updateBalance(user, newBalance);
    }

    @Override
    public void subtractBalance(User user, BigDecimal sum) throws ServiceException { //
        if(!validator.checkSum(sum)){
            throw new ServiceException("Invalid sum data");
        }

        BigDecimal newBalance = user.getBalance().subtract(sum);
        user.setBalance(newBalance);
        update(user);
        //updateBalance(user, newBalance);
    }

    @Override
    public void block(User user) throws ServiceException {
        user.setBlocked(true);

        update(user);
    }

    @Override
    public void delete(User user) throws ServiceException {
        try {
            userDAO.delete(user);
            logger.debug("User " + user.getLogin() + "deleted");
        } catch (DAOException e) {
            logger.error("Can't delete user " + user.getLogin(), e);
            throw new ServiceException(e);
        }
    }
}
