package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.Const;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.LotDAO;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
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
        try{
            List<Lot> lots = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt("lot_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                BigDecimal startPrice = resultSet.getBigDecimal("start_price");
                BigDecimal minPrice = resultSet.getBigDecimal("min_price");
                int ownerId = resultSet.getInt("owner_id");
                int buyerId = resultSet.getInt("buyer_id");

                Lot lot = new Lot(id, name, description, location, ownerId, buyerId, startPrice, minPrice);
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
        return Const.SELECT_LOT_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return Const.SELECT_ALL_LOTS;
    }

    @Override
    protected String getInsertQuery() {
        return Const.INSERT_LOT;
    }

    @Override
    protected String getUpdateQuery() {
        return Const.UPDATE_LOT_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return Const.DELETE_LOT_BY_ID;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Lot lot) throws DAOException {
        try {
            statement.setString(1, lot.getName());
            statement.setString(2, lot.getDescription());
            statement.setString(3, lot.getLocation());
            statement.setBigDecimal(4, lot.getStartPrice());
            statement.setBigDecimal(5, lot.getMinPrice());
            statement.setInt(6, lot.getOwnerId());
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
            statement.setBigDecimal(5, lot.getMinPrice());
            statement.setInt(6, lot.getOwnerId());
            statement.setInt(7, lot.getId());
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
            statement = connection.prepareStatement(Const.SELECT_LOT_BY_OWNER_ID);

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
    public List<Lot> findByBuyerId(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(Const.SELECT_LOT_BY_BUYER_ID);

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

    /*@Override
    public void updateDescription(Lot lot, String newDescription) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_LOT_DESCRIPTION);

            preparedStatement.setString(1, newDescription);
            preparedStatement.setString(2, String.valueOf(lot.getId()));

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
    public void updateLocation(Lot lot, String newLocation) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_LOT_LOCATION);

            preparedStatement.setString(1, newLocation);
            preparedStatement.setString(2, String.valueOf(lot.getId()));

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
    public void updateStartPrice(Lot lot, BigDecimal newStartPrice) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_LOT_START_PRICE);

            preparedStatement.setString(1, String.valueOf(newStartPrice));
            preparedStatement.setString(2, String.valueOf(lot.getId()));

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
    public void updateMinPrice(Lot lot, BigDecimal newMinPrice) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_LOT_MIN_PRICE);

            preparedStatement.setString(1, String.valueOf(newMinPrice));
            preparedStatement.setString(2, String.valueOf(lot.getId()));

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
