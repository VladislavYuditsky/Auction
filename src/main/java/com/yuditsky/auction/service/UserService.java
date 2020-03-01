package com.yuditsky.auction.service;

import com.yuditsky.auction.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    boolean save(User user) throws ServiceException;

    User findByLoginAndPassword(String login, String password) throws ServiceException;

    List<User> findAll() throws ServiceException;

    User findByLogin(String login) throws ServiceException;

    User findById(int id) throws ServiceException;

    void updateSettings(User user, String email, String password) throws ServiceException;

    void update(User user) throws ServiceException;

    void addBalance(User user, BigDecimal sum) throws ServiceException;

    void subtractBalance(User user, BigDecimal sum) throws ServiceException;

    void changeBlockStatus(User user) throws ServiceException;

    void delete(User user) throws ServiceException;

    void blockDebtors() throws ServiceException;
}
