package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.*;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.dao.impl.util.QueryProvider;
import com.yuditsky.auction.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.DBColumnNamesProvider.*;

public class SQLUserDAO extends SQLAbstractDAO<User> implements UserDAO {
    private final static Logger logger = LogManager.getLogger(SQLUserDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws DAOException {
        try {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(USER_ID);
                String login = resultSet.getString(LOGIN);
                String password = resultSet.getString(PASSWORD);
                String email = resultSet.getString(EMAIL);
                UserRole role = UserRole.valueOf(resultSet.getString(ROLE).toUpperCase());
                boolean blocked = resultSet.getBoolean(BLOCKED);
                BigDecimal balance = resultSet.getBigDecimal(BALANCE);

                User user = new User(id, login, password, role, email, balance, blocked);
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            logger.error("Can't parse resultSet", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected String getSelectByIdQuery() {
        return QueryProvider.SELECT_USER_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return QueryProvider.SELECT_ALL_USERS;
    }

    @Override
    protected String getInsertQuery() {
        return QueryProvider.INSERT_USER;
    }

    @Override
    protected String getUpdateQuery() {
        return QueryProvider.UPDATE_USER_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return QueryProvider.DELETE_USER_BY_ID;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User user) throws DAOException {
        try {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
        } catch (SQLException e) {
            logger.error("Can't prepare statement fot insert", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User user) throws DAOException {
        try {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getEmail());
            statement.setString(3, String.valueOf(user.getRole()));
            statement.setBoolean(4, user.isBlocked());
            statement.setBigDecimal(5, user.getBalance());
            statement.setInt(6, user.getId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement fot insert", e);
            throw new DAOException(e);
        }
    }

    @Override
    public User findByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_USER_BY_LOGIN);

            statement.setString(1, String.valueOf(login));

            resultSet = statement.executeQuery();

            List<User> users = parseResultSet(resultSet);

            if (users.size() == 0) {
                return null;
            }

            if (users.size() > 1) {
                logger.error("More than one record found by login");
                throw new DAOException();
            }

            return users.iterator().next();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL error", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
    }

    @Override
    public void blockDebtors() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.BLOCK_DEBTORS);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL error", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }
}
