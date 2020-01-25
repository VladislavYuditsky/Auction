package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    boolean save(User user) throws ServiceException;

    User findByLoginAndPassword(String login, String password) throws ServiceException;

    List<User> findAll() throws ServiceException;

    User findUserByLogin(String login) throws ServiceException;

    void updatePassword(User user, String password) throws ServiceException;

    void updateEmail(User user, String email) throws ServiceException;

    void updateBalance(User user, BigDecimal sum) throws ServiceException;

    void addBalance(User user, BigDecimal sum) throws ServiceException;

    void subtractBalance(User user, BigDecimal sum) throws ServiceException;

    void delete(User user) throws ServiceException;
}
