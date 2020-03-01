package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.AuctionDAO;
import com.yuditsky.auction.dao.BidDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.DAOFactory;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.dao.impl.util.QueryProvider;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionStatus;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Bid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.yuditsky.auction.dao.impl.util.DBColumnNamesProvider.*;

public class SQLAuctionDAO extends SQLAbstractDAO<Auction> implements AuctionDAO {
    private final static Logger logger = LogManager.getLogger(SQLAuctionDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected List<Auction> parseResultSet(ResultSet resultSet) throws DAOException {
        try {
            List<Auction> auctions = new ArrayList<>();
            while (resultSet.next()) {
                int auctionId = resultSet.getInt(AUCTION_ID);
                AuctionType type = AuctionType.valueOf(resultSet.getString(AUCTION_TYPE).toUpperCase());
                int lotId = resultSet.getInt(LOT_ID);
                AuctionStatus status = AuctionStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
                int winnerId = resultSet.getInt(WINNER_ID);

                Auction auction = new Auction(auctionId, type, lotId, status, winnerId);
                auctions.add(auction);
            }

            return auctions;
        } catch (SQLException e) {
            logger.error("Can't parse resultSet", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected String getSelectByIdQuery() {
        return QueryProvider.SELECT_AUCTION_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return QueryProvider.SELECT_ALL_AUCTIONS;
    }

    @Override
    protected String getInsertQuery() {
        return QueryProvider.INSERT_AUCTION;
    }

    @Override
    protected String getUpdateQuery() {
        return QueryProvider.UPDATE_AUCTION_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return QueryProvider.DELETE_AUCTION_BY_ID;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Auction auction) throws DAOException {
        try {
            statement.setString(1, String.valueOf(auction.getType()));
            statement.setInt(2, auction.getLotId());
            statement.setString(3, String.valueOf(auction.getStatus()));
            statement.setInt(4, auction.getWinnerId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for insert auction", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Auction auction) throws DAOException {
        try {
            statement.setString(1, String.valueOf(auction.getType()));
            statement.setInt(2, auction.getLotId());
            statement.setString(3, String.valueOf(auction.getStatus()));
            statement.setInt(4, auction.getWinnerId());
            statement.setInt(5, auction.getId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for update auction", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Auction> findByType(AuctionType type) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_AUCTION_BY_TYPE);

            statement.setString(1, String.valueOf(type));

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
    public List<Auction> findByStatus(AuctionStatus status) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_AUCTION_BY_STATUS);

            statement.setString(1, String.valueOf(status));

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
    public List<Auction> findByStatus(AuctionStatus status, int limit, int offset) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_AUCTION_BY_STATUS_WITH_LIMIT_AND_OFFSET);

            statement.setString(1, String.valueOf(status));
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
    public List<Auction> findByWinnerId(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_AUCTION_BY_WINNER_ID);

            statement.setString(1, String.valueOf(id));

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
    public Auction findByLotId(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_AUCTION_BY_LOT_ID);

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            List<Auction> auctions = parseResultSet(resultSet);

            if (auctions.size() == 0) {
                return null;
            }

            if (auctions.size() > 1) {
                logger.error("More than one record found by lot id");
                throw new DAOException();
            }

            return auctions.iterator().next();
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
