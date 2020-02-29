package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.dao.impl.util.QueryProvider;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.LotDAO;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLLotDAO extends SQLAbstractDAO<Lot> implements LotDAO {
    private final static Logger logger = LogManager.getLogger(SQLLotDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected List<Lot> parseResultSet(ResultSet resultSet) throws DAOException {
        try {
            List<Lot> lots = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("lot_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                BigDecimal startPrice = resultSet.getBigDecimal("start_price");
                int ownerId = resultSet.getInt("owner_id");

                Lot lot = new Lot(id, name, description, location, ownerId, startPrice);
                lots.add(lot);
            }

            return lots;
        } catch (SQLException e) {
            logger.error("Can't parse resultSet", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected String getSelectByIdQuery() {
        return QueryProvider.SELECT_LOT_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return QueryProvider.SELECT_ALL_LOTS;
    }

    @Override
    protected String getInsertQuery() {
        return QueryProvider.INSERT_LOT;
    }

    @Override
    protected String getUpdateQuery() {
        return QueryProvider.UPDATE_LOT_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return QueryProvider.DELETE_LOT_BY_ID;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Lot lot) throws DAOException {
        try {
            statement.setString(1, lot.getName());
            statement.setString(2, lot.getDescription());
            statement.setString(3, lot.getLocation());
            statement.setBigDecimal(4, lot.getStartPrice());
            statement.setInt(5, lot.getOwnerId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for insert", e);
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Lot lot) throws DAOException {
        try {
            statement.setString(1, lot.getName());
            statement.setString(2, lot.getDescription());
            statement.setString(3, lot.getLocation());
            statement.setBigDecimal(4, lot.getStartPrice());
            statement.setInt(5, lot.getOwnerId());
            statement.setInt(6, lot.getId());
        } catch (SQLException e) {
            logger.error("Can't prepare statement for insert", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Lot> findByOwnerId(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_LOT_BY_OWNER_ID);

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
    public List<Lot> findByOwnerId(int id, int limit, int offset) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_LOT_BY_OWNER_ID_WITH_LIMIT_AND_OFFSET);

            statement.setInt(1, id);
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
    public List<Lot> findAwaitingPaymentLots(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_AWAITING_PAYMENT_LOTS);

            statement.setInt(1, userId);

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
    public List<Lot> findAwaitingPaymentLots(int userId, int limit, int offset) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_AWAITING_PAYMENT_LOTS_WITH_LIMIT_AND_OFFSET);

            statement.setInt(1, userId);
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
    public List<Lot> findActiveLotByAuctionType(AuctionType type) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_ACTIVE_LOT_BY_AUCTION_TYPE);

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
    public List<Lot> findActiveLotByAuctionType(AuctionType type,int limit, int offset) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_ACTIVE_LOT_BY_AUCTION_TYPE_WITH_LIMIT_AND_OFFSET);

            statement.setString(1, String.valueOf(type));
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
    public List<Lot> findLotsWithUserBids(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_LOTS_WITH_USER_BIDS);

            statement.setInt(1, userId);

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
    public List<Lot> findLotsWithUserBids(int userId, int limit, int offset) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(QueryProvider.SELECT_LOTS_WITH_USER_BIDS_WITH_LIMIT_AND_OFFSET);

            statement.setInt(1, userId);
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
}
