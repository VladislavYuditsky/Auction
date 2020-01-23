package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.Const;
import com.yuditsky.auction.dao.BidDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Bid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.yuditsky.auction.Const.DATA_TIME_FORMATTER;

public class SQLBidDAO implements BidDAO {
    private final static Logger logger = LogManager.getLogger(SQLBidDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addBid(Bid bid) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.ADD_NEW_BID);

            preparedStatement.setString(1, String.valueOf(bid.getBidderId()));
            preparedStatement.setString(2, String.valueOf(bid.getSum()));
            preparedStatement.setString(3, String.valueOf(bid.getTime()));
            preparedStatement.setString(4, String.valueOf(bid.getAuctionId()));

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
    public Bid findBidById(int bidId) throws DAOException { //trabl millisec
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_BID_BY_ID);

            preparedStatement.setString(1, String.valueOf(bidId));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.first();
            Bid bid = createBid(resultSet);

            connectionPool.closeConnection(connection, preparedStatement);

            return bid;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void changeBidderId(int bidId, int newBidderId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_BID_BIDDER_ID);

            preparedStatement.setString(1, String.valueOf(newBidderId));
            preparedStatement.setString(2, String.valueOf(bidId));

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
    public void changeSum(int bidId, BigDecimal newSum) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_BID_SUM);

            preparedStatement.setString(1, String.valueOf(newSum));
            preparedStatement.setString(2, String.valueOf(bidId));

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
    public void changeTime(int bidId, LocalDateTime newTime) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_BID_TIME);

            preparedStatement.setString(1, String.valueOf(newTime));
            preparedStatement.setString(2, String.valueOf(bidId));

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
    public void changeAuctionId(int bidId, int newAuctionId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_BID_AUCTION_ID);

            preparedStatement.setString(1, String.valueOf(newAuctionId));
            preparedStatement.setString(2, String.valueOf(bidId));

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
    public void deleteBidById(int bidId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.DELETE_BID_BY_ID);

            preparedStatement.setString(1, String.valueOf(bidId));

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

    private Bid createBid(ResultSet resultSet) throws SQLException {
        int bidId = resultSet.getInt("bid_id");
        int bidderId = resultSet.getInt("bidder_id");
        BigDecimal sum = resultSet.getBigDecimal("sum");
        LocalDateTime time = LocalDateTime.parse(resultSet.getString("time"), DATA_TIME_FORMATTER);
        int auctionId = resultSet.getInt("auction_id");

        return new Bid(bidId, bidderId, sum, time, auctionId);
    }
}