package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;

import java.math.BigDecimal;

public interface UserDAO {
    User signIn(String login, String password) throws DAOException;

    void signUp(User newUser) throws DAOException;

    User findUserByLogin(String login) throws DAOException;

    void changePassword(String login, String newPassword) throws DAOException;

    void changeEmail(String login, String newEmail) throws DAOException;

    void changeBalance(String login, BigDecimal newBalance) throws DAOException;

    void changeRole(String login, UserRole newUserRole) throws DAOException;

    void deleteUser(String login) throws DAOException;
}
