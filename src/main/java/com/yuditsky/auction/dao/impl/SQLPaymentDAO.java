package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.Const;
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

import static com.yuditsky.auction.Const.DATA_TIME_FORMATTER;

public class SQLPaymentDAO implements PaymentDAO {
    private final static Logger logger = LogManager.getLogger(SQLPaymentDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void save(Payment payment) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.ADD_NEW_PAYMENT);

            preparedStatement.setString(1, String.valueOf(payment.getPayerId()));
            preparedStatement.setString(2, String.valueOf(payment.getSum()));
            preparedStatement.setString(3, String.valueOf(payment.getLotId()));
            preparedStatement.setString(4, String.valueOf(payment.getDate()));

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
    public Payment findById(int paymentId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_PAYMENT_BY_ID);

            preparedStatement.setString(1, String.valueOf(paymentId));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.first();
            Payment payment = createPayment(resultSet);

            connectionPool.closeConnection(connection, preparedStatement);

            return payment;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Payment> findByPayerId(int payerId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_PAYMENT_BY_PAYER_ID);

            preparedStatement.setString(1, String.valueOf(payerId));

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Payment> payments = new ArrayList<>();
            while (resultSet.next()) {
                Payment payment = createPayment(resultSet);
                payments.add(payment);
            }

            connectionPool.closeConnection(connection, preparedStatement);

            return payments;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void updatePayerId(Payment payment, int newPayerId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_PAYMENT_PAYER_ID);

            preparedStatement.setString(1, String.valueOf(newPayerId));
            preparedStatement.setString(2, String.valueOf(payment.getPaymentId()));

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
    public void updateSum(Payment payment, BigDecimal newSum) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_PAYMENT_SUM);

            preparedStatement.setString(1, String.valueOf(newSum));
            preparedStatement.setString(2, String.valueOf(payment.getPaymentId()));

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
            preparedStatement.setString(2, String.valueOf(payment.getPaymentId()));

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
            preparedStatement.setString(2, String.valueOf(payment.getPaymentId()));

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
    public void delete(Payment payment) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.DELETE_PAYMENT_BY_ID);

            preparedStatement.setString(1, String.valueOf(payment.getPaymentId()));

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

    private Payment createPayment(ResultSet resultSet) throws SQLException {
        int paymentId = resultSet.getInt("payment_id");
        int payerId = resultSet.getInt("payer_id");
        BigDecimal sum = resultSet.getBigDecimal("sum");
        int lotId = resultSet.getInt("lot_id");
        LocalDateTime date = LocalDateTime.parse(resultSet.getString("date"), DATA_TIME_FORMATTER);

        return new Payment(paymentId, payerId, sum, lotId, date);
    }
}
