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

            if(user != null && !encoder.checkPassword(password, user.getPassword())){
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
    public void updateSettings(User user, String email, String password) throws ServiceException {
        if (password != null && !password.equals("")) {
            updatePassword(user, password);
        }

        if (email != null && !email.equals("")) {
            user.setEmail(email);
        }

        if (password != null | email != null) {
            update(user);
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

    @Override
    public void addBalance(User user, BigDecimal sum) throws ServiceException {
        if(!validator.checkBigDecimal(sum)){
            throw new ServiceException("Invalid sum data");
        }

        BigDecimal newBalance = user.getBalance().add(sum);
        user.setBalance(newBalance);
        update(user);
    }

    @Override
    public void subtractBalance(User user, BigDecimal sum) throws ServiceException {
        if(!validator.checkBigDecimal(sum)){
            throw new ServiceException("Invalid sum data");
        }

        BigDecimal newBalance = user.getBalance().subtract(sum);
        user.setBalance(newBalance);
        update(user);
    }

    @Override
    public void changeBlockStatus(User user) throws ServiceException {
        if(user.isBlocked()){
            user.setBlocked(false);
        } else {
            user.setBlocked(true);
        }

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

    private void updatePassword(User user, String password) throws ServiceException {
        if(!validator.checkPassword(password)){
            throw new ServiceException("Invalid password");
        }

        try {
            user.setPassword(encoder.encode(password));
            userDAO.update(user);
        } catch (DAOException e) {
            logger.error("Can't update password for user " + user.getLogin(), e);
            throw new ServiceException(e);
        }
    }
}
