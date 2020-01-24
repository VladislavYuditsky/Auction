package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.Const;
import com.yuditsky.auction.dao.CreditDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Credit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.Const.DATA_TIME_FORMATTER;

public class SQLCreditDAO implements CreditDAO {
    private final static Logger logger = LogManager.getLogger(SQLCreditDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void save(Credit credit) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.ADD_NEW_CREDIT);

            preparedStatement.setString(1, String.valueOf(credit.getCreditId()));
            preparedStatement.setString(2, String.valueOf(credit.getPercent()));
            preparedStatement.setString(3, String.valueOf(credit.getEndDate()));
            preparedStatement.setString(4, String.valueOf(credit.getBalance()));
            preparedStatement.setString(5, String.valueOf(credit.getBorrowerId()));

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
    public Credit findById(int creditId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_CREDIT_BY_ID);

            preparedStatement.setString(1, String.valueOf(creditId));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.first();
            Credit credit = createCredit(resultSet);

            connectionPool.closeConnection(connection, preparedStatement);

            return credit;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Credit> findByBorrowerId(int borrowerId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_CREDIT_BY_BORROWER_ID);

            preparedStatement.setString(1, String.valueOf(borrowerId));

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Credit> credits = new ArrayList<>();
            while (resultSet.next()) {
                Credit credit = createCredit(resultSet);
                credits.add(credit);
            }

            connectionPool.closeConnection(connection, preparedStatement);

            return credits;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Credit> findAll() throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(Const.SELECT_ALL_CREDITS);

            List<Credit> credits = new ArrayList<>();
            while (resultSet.next()) {
                Credit credit = createCredit(resultSet);
                credits.add(credit);
            }

            connectionPool.closeConnection(connection, statement);

            return credits;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void updateBalance(Credit credit, int newBalance) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_CREDIT_BALANCE);

            preparedStatement.setString(1, String.valueOf(newBalance));
            preparedStatement.setString(2, String.valueOf(credit.getCreditId()));

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
    public void delete(Credit credit) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.DELETE_BID_BY_ID);

            preparedStatement.setString(1, String.valueOf(credit.getCreditId()));

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

    private Credit createCredit(ResultSet resultSet) throws SQLException {
        int creditId = resultSet.getInt("credit_id");
        double percent = resultSet.getDouble("percent");
        LocalDateTime endDate = LocalDateTime.parse(resultSet.getString("end_date"), DATA_TIME_FORMATTER);
        BigDecimal balance = resultSet.getBigDecimal("balance");
        BigDecimal sum = resultSet.getBigDecimal("sum");
        int borrowerId = resultSet.getInt("borrower_id");

        return new Credit(creditId, percent, endDate, balance, sum, borrowerId);
    }
}
