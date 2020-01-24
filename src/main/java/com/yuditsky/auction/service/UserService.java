package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    boolean addUser(User user) throws ServiceException;

    User findUserByLoginAndPassword(String login, String password) throws ServiceException;

    List<User> findAll() throws ServiceException;

    User findUserByLogin(String login) throws ServiceException;

    void updatePassword(User user, String password) throws ServiceException;

    void updateEmail(User user, String email) throws ServiceException;

    void updateBalance(User user, BigDecimal sum) throws ServiceException;

    void addBalance(User user, BigDecimal sum) throws ServiceException;

    void subtractBalance(User user, BigDecimal sum) throws ServiceException;

    void deleteUser(User user) throws ServiceException;
}
