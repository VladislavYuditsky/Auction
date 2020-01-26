package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.Const;
import com.yuditsky.auction.dao.*;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO extends SQLAbstractDAO<User> implements UserDAO {
    private final static Logger logger = LogManager.getLogger(SQLUserDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws DAOException {
        try {
            List<User> users = new ArrayList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                UserRole role = UserRole.valueOf(resultSet.getString("role").toUpperCase());
                boolean blocked = resultSet.getBoolean("blocked");
                BigDecimal balance = resultSet.getBigDecimal("balance");

                DAOFactory factory = DAOFactory.getInstance();
                BidDAO bidDAO = factory.getBidDAO();
                List<Bid> bids = bidDAO.findByBidderId(id);

                LotDAO lotDAO = factory.getLotDAO();
                List<Lot> lots = lotDAO.findByOwnerId(id);

                PaymentDAO paymentDAO = factory.getPaymentDAO();
                List<Payment> payments = paymentDAO.findByPayerId(id);

                CreditDAO creditDAO = factory.getCreditDAO();
                List<Credit> credits = creditDAO.findByBorrowerId(id);

                User user = new User(id, login, password, role, email, balance, blocked, bids, lots, payments, credits);
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
        return Const.SELECT_USER_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return Const.SELECT_ALL_USERS;
    }

    @Override
    protected String getInsertQuery() {
        return Const.INSERT_USER;
    }

    @Override
    protected String getUpdateQuery() {
        return Const.UPDATE_USER_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return Const.DELETE_USER_BY_ID;
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
            statement = connection.prepareStatement(Const.SELECT_USER_BY_LOGIN);

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
    public User findByLoginAndPassword(String login, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(Const.SELECT_USER_BY_LOGIN_AND_PASSWORD);

            statement.setString(1, String.valueOf(login));
            statement.setString(2, String.valueOf(password));

            resultSet = statement.executeQuery();

            List<User> users = parseResultSet(resultSet);

            if (users.size() == 0) {
                return null;
            }

            if (users.size() > 1) {
                logger.error("More than one record found by login and password");
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

    /*@Override
    public void updatePassword(User user, String newPassword) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_USER_PASSWORD);

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, user.getLogin());

            preparedStatement.executeUpdate();

            connectionPool.closeConnection(connection, preparedStatement);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void updateEmail(User user, String newEmail) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_USER_EMAIL);

            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, user.getLogin());

            preparedStatement.executeUpdate();

            connectionPool.closeConnection(connection, preparedStatement);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void updateBalance(User user, BigDecimal newBalance) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_USER_BALANCE);

            preparedStatement.setString(1, String.valueOf(newBalance));
            preparedStatement.setString(2, user.getLogin());

            preparedStatement.executeUpdate();

            connectionPool.closeConnection(connection, preparedStatement);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void updateRole(User user, UserRole newUserRole) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_USER_ROLE);

            preparedStatement.setString(1, String.valueOf(newUserRole));
            preparedStatement.setString(2, user.getLogin());

            preparedStatement.executeUpdate();

            connectionPool.closeConnection(connection, preparedStatement);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }*/
}
