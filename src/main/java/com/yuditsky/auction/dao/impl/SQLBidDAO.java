package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.impl.util.QueryProvider;
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
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.Constant.DATA_TIME_FORMATTER;

public class SQLBidDAO extends SQLAbstractDAO<Bid> implements BidDAO {
    private final static Logger logger = LogManager.getLogger(SQLBidDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected List<Bid> parseResultSet(ResultSet resultSet) throws DAOException {
        try {
            List<Bid> bids = new ArrayList<>();
            while (resultSet.next()) {
                int bidId = resultSet.getInt("bid_id");
                int bidderId = resultSet.getInt("bidder_id");
                BigDecimal sum = resultSet.getBigDecimal("sum");
                LocalDateTime time = LocalDateTime.parse(resultSet.getString("time"), DATA_TIME_FORMATTER);
                int auctionId = resultSet.getInt("auction_id");

                Bid bid = new Bid(bidId, bidderId, sum, time, auctionId);
                bids.add(bid);
            }

            return bids;
        } catch (SQLException e) {
            logger.error("Can't parse resultset", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected String getSelectByIdQuery() {
        return QueryProvider.SELECT_BID_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return QueryProvider.SELECT_ALL_BIDS;
    }

    @Override
    protected String getInsertQuery() {
        return QueryProvider.INSERT_BID;
    }

    @Override
    protected String getUpdateQuery() {
        return QueryProvider.UPDATE_BID_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return QueryProvider.DELETE_BID_BY_ID;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Bid bid) throws DAOException {
        try {
            statement.setInt(1, bid.getBidderId());
            statement.setBigDecimal(2, bid.getSum());
            statement.setString(3, String.valueOf(bid.getTime()));
            statement.setInt(4, bid.getAuctionId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for insert", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Bid bid) throws DAOException {
        try {
            statement.setInt(1, bid.getBidderId());
            statement.setBigDecimal(2, bid.getSum());
            statement.setString(3, String.valueOf(bid.getTime()));
            statement.setInt(4, bid.getAuctionId());
            statement.setInt(5, bid.getId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for insert", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Bid> findByAuctionId(int auctionId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_BID_BY_AUCTION_ID);

            statement.setInt(1, auctionId);

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
    public List<Bid> findByBidderId(int bidderId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_BID_BY_BIDDER_ID);

            statement.setInt(1, bidderId);

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
    public Bid findWithMaxSumByAuctionId(int auctionId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_BID_WITH_MAX_SUM_BY_AUCTION_ID);

            statement.setInt(1, auctionId);

            resultSet = statement.executeQuery();

            List<Bid> bids = parseResultSet(resultSet);

            if (bids.size() == 0) {
                return null;
            }

            if (bids.size() > 1) {
                logger.error("More than one record found with max sum by auction id");
                throw new DAOException();
            }

            return bids.iterator().next();
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
    public Bid findWithMinSumByBidderIdAndAuctionId(int bidderId, int auctionId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_BID_WITH_MIN_SUM_BY_BIDDER_ID_AND_AUCTION_ID);

            statement.setInt(1, bidderId);
            statement.setInt(2, auctionId);

            resultSet = statement.executeQuery();

            List<Bid> bids = parseResultSet(resultSet);

            if (bids.size() == 0) {
                return null;
            }

            if (bids.size() > 1) {
                logger.error("More than one record found with min sum by bidder id and auction id");
                throw new DAOException();
            }

            return bids.iterator().next();
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
    public Bid findWithMinSumByAuctionId(int auctionId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_BID_WITH_MIN_SUM_BY_AUCTION_ID);

            statement.setInt(1, auctionId);

            resultSet = statement.executeQuery();

            List<Bid> bids = parseResultSet(resultSet);

            if (bids.size() == 0) {
                return null;
            }

            if (bids.size() > 1) {
                logger.error("More than one record found with min sum by auction id");
                throw new DAOException();
            }

            return bids.iterator().next();
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
