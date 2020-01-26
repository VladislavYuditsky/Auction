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

public class SQLCreditDAO extends SQLAbstractDAO<Credit> implements CreditDAO {
    private final static Logger logger = LogManager.getLogger(SQLCreditDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected List<Credit> parseResultSet(ResultSet resultSet) throws DAOException {
        try {
            List<Credit> credits = new ArrayList<>();
            while (resultSet.next()) {
                int creditId = resultSet.getInt("credit_id");
                double percent = resultSet.getDouble("percent");
                LocalDateTime endDate = LocalDateTime.parse(resultSet.getString("end_date"),
                        DATA_TIME_FORMATTER);
                BigDecimal balance = resultSet.getBigDecimal("balance");
                BigDecimal sum = resultSet.getBigDecimal("sum");
                int borrowerId = resultSet.getInt("borrower_id");

                Credit credit = new Credit(creditId, percent, endDate, balance, sum, borrowerId);
                credits.add(credit);
            }

            return credits;
        } catch (SQLException e) {
            logger.error("Can't parse resultSet", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected String getSelectByIdQuery() {
        return Const.SELECT_CREDIT_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return Const.SELECT_ALL_CREDITS;
    }

    @Override
    protected String getInsertQuery() {
        return Const.INSERT_CREDIT;
    }

    @Override
    protected String getUpdateQuery() {
        return Const.UPDATE_CREDIT_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return Const.DELETE_CREDIT_BY_ID;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Credit credit) throws DAOException {
        try {
            statement.setDouble(1, credit.getPercent());
            statement.setString(2, String.valueOf(credit.getEndDate()));
            statement.setBigDecimal(3, credit.getBalance());
            statement.setBigDecimal(4, credit.getSum());
            statement.setInt(5, credit.getBorrowerId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for insert", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Credit credit) throws DAOException {
        try {
            statement.setDouble(1, credit.getPercent());
            statement.setString(2, String.valueOf(credit.getEndDate()));
            statement.setBigDecimal(3, credit.getBalance());
            statement.setBigDecimal(4, credit.getSum());
            statement.setInt(5, credit.getBorrowerId());
            statement.setInt(6, credit.getId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for update", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Credit> findByBorrowerId(int borrowerId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(Const.SELECT_CREDIT_BY_BORROWER_ID);

            statement.setString(1, String.valueOf(borrowerId));

            resultSet = statement.executeQuery();

            return parseResultSet(resultSet);
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
    public void updateBalance(Credit credit, BigDecimal newBalance) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(Const.UPDATE_CREDIT_BALANCE);

            statement.setBigDecimal(1, newBalance);
            statement.setInt(2, credit.getId());

            resultSet = statement.executeQuery();
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
}
