package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;

import java.math.BigDecimal;
import java.util.List;

public interface UserDAO {
    User findUserByLoginAndPassword(String login, String password) throws DAOException;

    void save(User newUser) throws DAOException;

    List<User> findAll() throws DAOException;

    User findUserByLogin(String login) throws DAOException;

    void changePassword(User user, String newPassword) throws DAOException;

    void changeEmail(User user, String newEmail) throws DAOException;

    void changeBalance(User user, BigDecimal newBalance) throws DAOException;

    void changeRole(User user, UserRole newUserRole) throws DAOException;

    void deleteUser(User user) throws DAOException;
}
