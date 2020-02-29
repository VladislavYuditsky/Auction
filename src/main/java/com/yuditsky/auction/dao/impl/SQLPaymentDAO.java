package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.impl.util.QueryProvider;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.PaymentDAO;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Payment;
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

public class SQLPaymentDAO extends SQLAbstractDAO<Payment> implements PaymentDAO {
    private final static Logger logger = LogManager.getLogger(SQLPaymentDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected List<Payment> parseResultSet(ResultSet resultSet) throws DAOException {
        try {
            List<Payment> payments = new ArrayList<>();
            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                int payerId = resultSet.getInt("payer_id");
                BigDecimal sum = resultSet.getBigDecimal("sum");
                int lotId = resultSet.getInt("lot_id");
                LocalDateTime date = LocalDateTime.parse(resultSet.getString("date"), DATA_TIME_FORMATTER);

                Payment payment = new Payment(paymentId, payerId, sum, lotId, date);
                payments.add(payment);
            }

            return payments;
        } catch (SQLException e) {
            logger.error("Can't parse resulSet", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected String getSelectByIdQuery() {
        return QueryProvider.SELECT_PAYMENT_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return QueryProvider.SELECT_ALL_PAYMENTS;
    }

    @Override
    protected String getInsertQuery() {
        return QueryProvider.INSERT_PAYMENT;
    }

    @Override
    protected String getUpdateQuery() {
        return QueryProvider.UPDATE_PAYMENT_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return QueryProvider.DELETE_PAYMENT_BY_ID;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Payment payment) throws DAOException {
        try {
            statement.setInt(1, payment.getPayerId());
            statement.setBigDecimal(2, payment.getSum());
            statement.setInt(3, payment.getLotId());
            statement.setString(4, String.valueOf(payment.getDate()));
        } catch (SQLException e) {
            logger.error("Can't prepare statement for insert", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Payment payment) throws DAOException {
        try {
            statement.setInt(1, payment.getPayerId());
            statement.setBigDecimal(2, payment.getSum());
            statement.setInt(3, payment.getLotId());
            statement.setString(4, String.valueOf(payment.getDate()));
            statement.setInt(5, payment.getId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for update", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Payment> findByPayerId(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_PAYMENT_BY_PAYER_ID);

            statement.setInt(1, id);

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
    public List<Payment> findByPayerId(int payerId, int limit, int offset) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_PAYMENT_BY_PAYER_ID_WITH_LIMIT_AND_OFFSET);

            statement.setInt(1, payerId);
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

    /*@Override
    public void updateSum(Payment payment, BigDecimal newSum) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_PAYMENT_SUM);

            preparedStatement.setString(1, String.valueOf(newSum));
            preparedStatement.setString(2, String.valueOf(payment.getId()));

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
    public void updateLotId(Payment payment, int newLotId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_PAYMENT_LOT_ID);

            preparedStatement.setString(1, String.valueOf(newLotId));
            preparedStatement.setString(2, String.valueOf(payment.getId()));

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
    public void updateDate(Payment payment, LocalDateTime newDate) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_PAYMENT_DATE);

            preparedStatement.setString(1, String.valueOf(newDate));
            preparedStatement.setString(2, String.valueOf(payment.getId()));

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
