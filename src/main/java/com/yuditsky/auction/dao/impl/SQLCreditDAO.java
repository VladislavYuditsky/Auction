package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.impl.util.QueryProvider;
import com.yuditsky.auction.dao.CreditDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Credit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;

public class SQLCreditDAO extends SQLAbstractDAO<Credit> implements CreditDAO {
    private final static Logger logger = LogManager.getLogger(SQLCreditDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected List<Credit> parseResultSet(ResultSet resultSet) throws DAOException {
        try {
            List<Credit> credits = new ArrayList<>();
            while (resultSet.next()) {
                int creditId = resultSet.getInt("credit_id");
                BigDecimal percent = resultSet.getBigDecimal("percent");
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
        return QueryProvider.SELECT_CREDIT_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return QueryProvider.SELECT_ALL_CREDITS;
    }

    @Override
    protected String getInsertQuery() {
        return QueryProvider.INSERT_CREDIT;
    }

    @Override
    protected String getUpdateQuery() {
        return QueryProvider.UPDATE_CREDIT_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return QueryProvider.DELETE_CREDIT_BY_ID;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Credit credit) throws DAOException {
        try {
            statement.setBigDecimal(1, credit.getPercent());
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
            statement.setBigDecimal(1, credit.getPercent());
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
            statement = connection.prepareStatement(QueryProvider.SELECT_CREDIT_BY_BORROWER_ID);

            statement.setInt(1,borrowerId);

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
    public List<Credit> findByBorrowerId(int borrowerId, int limit, int offset) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_CREDIT_BY_BORROWER_ID_WITH_LIMIT_AND_OFFSET);

            statement.setInt(1,borrowerId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

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
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.UPDATE_CREDIT_BALANCE);

            statement.setBigDecimal(1, newBalance);
            statement.setInt(2, credit.getId());

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
