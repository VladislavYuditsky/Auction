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

public class SQLUserDAO implements UserDAO {
    private final static Logger logger = LogManager.getLogger(SQLUserDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public User findByLoginAndPassword(String login, String password) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_USER_BY_LOGIN_AND_PASSWORD);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.first();
            User user = createUser(resultSet);

            connectionPool.closeConnection(connection, preparedStatement, resultSet);

            return user;
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e); /////////////ne tolko
            throw new DAOException(e);
        }
    }

    @Override
    public void save(User newUser) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.ADD_NEW_USER);

            preparedStatement.setString(1, newUser.getLogin());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getEmail());

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
    public List<User> findAll() throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(Const.SELECT_ALL_USERS);

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }

            connectionPool.closeConnection(connection, statement);

            return users;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public User findByLogin(String login) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_USER_BY_LOGIN);

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.first();
            User user = createUser(resultSet);

            connectionPool.closeConnection(connection, preparedStatement);

            return user;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
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
    }

    @Override
    public void delete(User user) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.DELETE_USER_BY_LOGIN);

            preparedStatement.setString(1, user.getLogin());

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

    private User createUser(ResultSet resultSet) throws SQLException, DAOException {
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
        List<Lot> lots = lotDAO.findBySellerId(id);

        PaymentDAO paymentDAO = factory.getPaymentDAO();
        List<Payment> payments = paymentDAO.findByPayerId(id);

        CreditDAO creditDAO = factory.getCreditDAO();
        List<Credit> credits = creditDAO.findByBorrowerId(id);

        return new User(id, login, password, role, email, balance, blocked, bids, lots, payments, credits);
    }
}
