package com.yuditsky.auction.dao.impl;

import com.yuditsky.auction.Const;
import com.yuditsky.auction.dao.AuctionDAO;
import com.yuditsky.auction.dao.DAOException;
import com.yuditsky.auction.dao.connection.ConnectionPool;
import com.yuditsky.auction.dao.connection.ConnectionPoolException;
import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.AuctionType;
import com.yuditsky.auction.entity.Lot;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SQLAuctionDAO implements AuctionDAO {
    private final static Logger logger = LogManager.getLogger(SQLAuctionDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addAuction(Auction auction) throws DAOException {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Const.ADD_NEW_AUCTION);
            //(auction_type, lot_id, finish_time)"
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
        return null;
    }

    @Override
    public void changeAuctionType(int auctionId, AuctionType newType) throws DAOException {

    }

    @Override
    public void changeLot(int auctionId, Lot newLot) throws DAOException {

    }

    @Override
    public void changeFinishTime(int auctionId, LocalDateTime newFinishTime) throws DAOException {

    }

    @Override
    public void deleteAuction(int auctionId) throws DAOException {

    }
}
