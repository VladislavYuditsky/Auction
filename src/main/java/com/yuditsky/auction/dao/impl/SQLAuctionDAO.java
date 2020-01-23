package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.Const;
import com.yuditsky.auction.dao.AuctionDAO;
import com.yuditsky.auction.dao.BidDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static com.yuditsky.auction.Const.DATA_TIME_FORMATTER;

public class SQLAuctionDAO implements AuctionDAO {
    private final static Logger logger = LogManager.getLogger(SQLAuctionDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addAuction(Auction auction) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.ADD_NEW_AUCTION);
            preparedStatement.setString(1, String.valueOf(auction.getType()));
            preparedStatement.setString(2, String.valueOf(auction.getLotId()));
            preparedStatement.setString(3, String.valueOf(auction.getFinishTime()));

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
    public Auction findAuctionById(int auctionId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.SELECT_AUCTION_BY_ID);

            preparedStatement.setString(1, String.valueOf(auctionId));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.first();
            Auction auction = createAuction(resultSet);

            connectionPool.closeConnection(connection, preparedStatement);

            return auction;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error compiling sql request", e);///ne tolko
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Can't take connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void changeAuctionType(int auctionId, AuctionType newType) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_AUCTION_TYPE);

            preparedStatement.setString(1, String.valueOf(newType));
            preparedStatement.setString(2, String.valueOf(auctionId));

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
    public void changeLotId(int auctionId, int newLotId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_AUCTION_LOT_ID);

            preparedStatement.setString(1, String.valueOf(newLotId));
            preparedStatement.setString(2, String.valueOf(auctionId));

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
    public void changeFinishTime(int auctionId, LocalDateTime newFinishTime) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.UPDATE_AUCTION_FINISH_TIME);

            preparedStatement.setString(1, String.valueOf(newFinishTime));
            preparedStatement.setString(2, String.valueOf(auctionId));

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
    public void deleteAuction(int auctionId) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.DELETE_AUCTION_BY_ID);

            preparedStatement.setString(1, String.valueOf(auctionId));

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

    private Auction createAuction(ResultSet resultSet) throws SQLException, DAOException {
        int auctionId = resultSet.getInt("auction_id");
        AuctionType type = AuctionType.valueOf(resultSet.getString("auction_type").toUpperCase());
        int lotId = resultSet.getInt("lot_id");
        LocalDateTime finishTime = LocalDateTime.parse(resultSet.getString("finish_time"),
                DATA_TIME_FORMATTER);

        BidDAO bidDAO = new SQLBidDAO();
        List<Integer> bidIds = bidDAO.findBidIdsByAuctionId(auctionId);

        return new Auction(auctionId, type, lotId, bidIds, finishTime);
    }
}
