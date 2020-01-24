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

public class SQLLotDAO implements LotDAO {
    private final static Logger logger = LogManager.getLogger(SQLLotDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addLot(Lot lot) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.ADD_NEW_LOT);

            preparedStatement.setString(1, lot.getName());
            preparedStatement.setString(2, lot.getDescription());
            preparedStatement.setString(3, lot.getLocation());
            preparedStatement.setString(4, String.valueOf(lot.getStartPrice()));
            preparedStatement.setString(5, String.valueOf(lot.getMinPrice()));
            preparedStatement.setString(6, String.valueOf(lot.getSellerId()));

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
    public void deleteLotById(int lotId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.DELETE_LOT_BY_ID);

            preparedStatement.setString(1, String.valueOf(lotId));

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
    public Lot findLotById(int lotId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_LOT_BY_ID);

            preparedStatement.setString(1, String.valueOf(lotId));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.first();
            Lot lot = createLot(resultSet);

            connectionPool.closeConnection(connection, preparedStatement);

            return lot;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Integer> findLotIdsBySellerId(int sellerId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_LOT_IDS_BY_SELLER_ID);

            preparedStatement.setString(1, String.valueOf(sellerId));

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Integer> lotIds = new ArrayList<>();
            while (resultSet.next()) {
                Lot lot = createLot(resultSet);
                lotIds.add(lot.getLotId());
            }

            connectionPool.closeConnection(connection, preparedStatement);

            return lotIds;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void changeDescription(int lotId, String newDescription) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_LOT_DESCRIPTION);

            preparedStatement.setString(1, newDescription);
            preparedStatement.setString(2, String.valueOf(lotId));

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
    public void changeLocation(int lotId, String newLocation) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_LOT_LOCATION);

            preparedStatement.setString(1, newLocation);
            preparedStatement.setString(2, String.valueOf(lotId));

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
    public void changeStartPrice(int lotId, BigDecimal newStartPrice) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_LOT_START_PRICE);

            preparedStatement.setString(1, String.valueOf(newStartPrice));
            preparedStatement.setString(2, String.valueOf(lotId));

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
    public void changeMinPrice(int lotId, BigDecimal newMinPrice) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_LOT_MIN_PRICE);

            preparedStatement.setString(1, String.valueOf(newMinPrice));
            preparedStatement.setString(2, String.valueOf(lotId));

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

    private Lot createLot(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("lot_id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String location = resultSet.getString("location");
        BigDecimal startPrice = resultSet.getBigDecimal("start_price");
        BigDecimal minPrice = resultSet.getBigDecimal("min_price");
        int sellerId = resultSet.getInt("seller_id");

        return new Lot(id, name, description, location, sellerId, startPrice, minPrice);
    }
}
