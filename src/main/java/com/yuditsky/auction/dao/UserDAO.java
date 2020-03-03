package com.yuditsky.auction.dao;

import com.yuditsky.auction.entity.User;
import com.yuditsky.auction.entity.UserRole;

import java.math.BigDecimal;
import java.util.List;

public interface UserDAO extends GenericDAO<User> {

    User findByLogin(String login) throws DAOException;
}
