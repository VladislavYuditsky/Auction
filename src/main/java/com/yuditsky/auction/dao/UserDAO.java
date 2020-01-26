package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;

import java.math.BigDecimal;
import java.util.List;

public interface UserDAO extends GenericDAO<User> {
    User findByLoginAndPassword(String login, String password) throws DAOException;

    User findByLogin(String login) throws DAOException;

    /*void updatePassword(User user, String newPassword) throws DAOException;

    void updateEmail(User user, String newEmail) throws DAOException;

    void updateBalance(User user, BigDecimal newBalance) throws DAOException;

    void updateRole(User user, UserRole newUserRole) throws DAOException;*/
}
